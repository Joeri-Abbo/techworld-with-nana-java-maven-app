
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
                    def dockerCmd = "docker run -d -p 8080:8080 $(IMAGE_NAME)"
                    sshagent(['ec2-server-key']){
                        sh "ssh -o StrictHostKeyChecking=no docker@docker@joeriabbo.nl $(dockerCmd)"
                    }
                }
            }
        }
    }   
}
