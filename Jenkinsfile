def gv

pipeline {
    agent any
    stages {
        stage("increment version") {
            steps {
                script {
                    echo 'incrementing app version...'
                    sh 'mvn build-helper:parse-version versions:set -DnewVersion=\${parsedVersion.majorVersion}.\${parsedVersion.minorVersion}.\${parsedVersion.nextIncrementalVersion} versions:commit'
                    def matcher = readFile('pom.xml') =~ /<version>(.*)<\/version>/
                    def version = matcher[0][1]
                    env.IMAGE_NAME = "$version-$buildNumber"
                }
            }
        }
          stage("init") {
            steps {
                script {
                    gv = load "script.groovy"
                }
            }
        }
        stage("build jar") {
            steps {
                script {
                    echo "building the application..."
                    sh 'mvn clean package'
                }
            }
        }
        stage("build image") {
            steps {
                script {
                    echo "building image"
 echo "building the docker image..."
    withCredentials([usernamePassword(credentialsId: 'docker-hub-repo', passwordVariable: 'PASS', usernameVariable: 'USER')]) {
        sh "docker build -t $(IMAGE_NAME) ."
        sh "echo $PASS | docker login -u $USER --password-stdin"
        sh "docker push $(IMAGE_NAME)"
    }                }
            }
        }
        stage("deploy") {
            steps {
                script {
                    echo "deploying"
                    gv.deployApp()
                }
            }
        }
    }   
}
