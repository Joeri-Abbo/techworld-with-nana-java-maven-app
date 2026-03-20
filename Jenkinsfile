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
                    
                    sh "docker build -t piratehammad/demo-app:$IMAGE_NAME ."
                    sh "echo \$PASS | docker login -u \$USER --password-stdin"
                    
                    sh "docker push piratehammad/demo-app:$IMAGE_NAME"
                }
            }
        }
        stage("deploy") {
            steps {
                echo 'deploying the application...'
            }
        }
    }
}
