pipeline {
    agent any
    tools {
        maven 'maven-3.92'
    }
    stages {
        stage('increment version') {
            steps {
                script {
                    echo "incrementing the version number..."
                    sh 'mvn build-helper:parse-version versions:set \
                    -DnewVersion=\\\${parsedVersion.majorVersion}.\\\${parsedVersion.minorVersion}.\\\${parsedVersion.nextIncrementalVersion} \
                    versions:commit'
                    def matcher = readFile('pom.xml') =~ '<version>(.+)</version>'
                    def version = matcher[0][1]
                    env.IMAGE_NAME = "${version}-${BUILD_NUMBER}"
                }
            }
        }
        stage("build jar") {
            steps {
                echo 'building the application...'
                sh 'mvn clean package'
            }
        }
        stage("build image") {
            steps {
                withCredentials([usernamePassword(
                    credentialsId: 'docker-hub-repo',
                    passwordVariable: 'PASS',
                    usernameVariable: 'USER'
                )]) {
                    sh "echo \$PASS | docker login -u \$USER --password-stdin"
                    sh "docker build -t piratehammad/demo-app:$IMAGE_NAME ."
                    sh "docker push piratehammad/demo-app:$IMAGE_NAME"
                }
            }
        }
        stage("deploy") {
            steps {
                echo 'deploying the application...'
            }
        }
        stage('commit version update') {  // ← added { here
            steps {
                script {
                    withCredentials([usernamePassword(
                        credentialsId: 'github-credentials',
                        passwordVariable: 'PASS',
                        usernameVariable: 'USER'
                    )]) {  // ← added { here
                        sh 'git config --global user.email "Hammad@example.com"'
                        sh 'git config --global user.name "Hammad"'
                        sh 'git status'
                        sh 'git branch'
                        sh 'git config --list'
                        sh "git remote set-url origin https://${USER}:${PASS}@github.com/import-Hammad/techworld-with-nana-java-maven-app.git"
                        sh 'git add .'
                        sh 'git commit -m "increment version number to ${IMAGE_NAME}"'
                        sh 'git push origin HEAD:master'
                    }  // ← closes withCredentials
                }
            }
        }
    }
}