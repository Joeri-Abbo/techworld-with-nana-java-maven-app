
pipeline {
    agent any
    tools {
        maven: "Maven"
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
        stage('provision server'){
            environment {
                AWS_ACCESS_KEY_ID = credentials('jenkins_aws_access_key_id')
                AWS_SECRET_ACCESS_KEY = credentials('jenkins_aws_secret_access_key')
                TF_VAR_env_prefix = "test"
            }
            steps{
                script {
                    dir('terraform'){
                        sh "terraform init"
//                         sh "terraform destroy -auto-approve"
                        sh "terraform apply -auto-approve"
                        EC2_PUBLIC_IP = sh(
                            script: "terraform output ec2_public_ip"
                            returnStdout: true
                        )
                    }
                }
            }
        }
        stage("deploy") {
            environment {
                DOCKER_CREDS = credentials('docker-hub-repo')
            }
            steps {
                script {
                    echo "Wating for EC2 server to init"
                    sleep(time:90, unit:'SECONDS'
                    echo "deploying docker image to EC2..."
                    echo "EC2_PUBLIC_IP: ${EC2_PUBLIC_IP}"

                    def shellCmd = "bash ./server.sh ${IMAGE_NAME} ${DOCKER_CREDS_USER} ${DOCKER_CREDS_PSW}"
                    def ec2Instance = "docker@${EC2_PUBLIC_IP}"

                    sshagent(['server-ssh-key']){
                        sh "scp -o StrictHostKeyChecking=no server.sh ${ec2Instance}:/home/docker"
                        sh "scp -o StrictHostKeyChecking=no docker-compose.yml ${ec2Instance}:/home/docker"
                        sh "ssh -o StrictHostKeyChecking=no ${ec2Instance} $(shellCmd)"
                    }
                }
            }
        }
    }   
}
