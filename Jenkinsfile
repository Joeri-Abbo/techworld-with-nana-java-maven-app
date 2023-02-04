
pipeline {
    agent any
    environment {
        IMAGE_NAME = "joeriabbo/java-maven-app"
    }
    stages {
        stage("increment version") {
            steps {
                script {
                    echo 'incrementing app version...'
                    sh 'mvn build-helper:parse-version versions:set -DnewVersion=\${parsedVersion.majorVersion}.\${parsedVersion.minorVersion}.\${parsedVersion.nextIncrementalVersion} versions:commit'
                    def matcher = readFile('pom.xml') =~ /<version>(.*)<\/version>/
                    def version = matcher[0][1]
                    env.IMAGE_NAME = env.IMAGE_NAME + "$version-$buildNumber"
                }
            }
        }
        stage("build jar") {
            steps {
                script {
                    echo "building the application..."
                    buildJar()
                }
            }
        }
        stage("build image") {
            steps {
                script {
                    echo "building the docker image..."
                    buildImage(env.IMAGE_NAME)
                    dockerLogin()
                    dockerPush(env.IMAGE_NAME)
            }
        }
        stage("deploy") {
            steps {
                script {
                    echo "deploying"
                    def shellCmd = "bash ./server.sh ${IMAGE_NAME}"
                    sshagent(['ec2-server-key']){
                        sh "scp server.sh docker@docker@joeriabbo.nl:/home/docker"
                        sh "scp docker-compose.yml docker@docker@joeriabbo.nl:/home/docker"
                        sh "ssh -o StrictHostKeyChecking=no docker@docker@joeriabbo.nl $(shellCmd)"
                    }
                }
            }
        }
    }   
}
