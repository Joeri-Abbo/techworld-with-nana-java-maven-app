#!/usr/bin/env groovy
library identifier: 'jenkins-shared-lib@master', retriever: modernSCM(
    [$class: 'GitSCMSource',
    remote: 'https://github.com/import-Hammad/jenkins-shared-libraries-nana.git',
    credentialsId: 'github-credentials']
)

pipeline {
    agent any
    tools {
        maven 'maven-3.92'
    }
    environment {
        IMAGE_NAME = 'piratehammad/react-nodejs-app:1.0'
    }
    stages {
        stage('build app') {
            steps {
                echo 'building application jar...'
                buildjar()
            }
        }
        stage('build image') {
            steps {
                script {
                    echo 'building the docker image...'
                    buildimage(env.IMAGE_NAME)
                    dockerLogin()
                    dockerPush(env.IMAGE_NAME)
                }
            }
        }
        stage("deploy") {
            steps {
                script {
                    echo 'deploying docker image to EC2...'
                    def dockerCmd = "docker run -p 3080:3080 -d ${IMAGE_NAME}"
                    sshagent(['ec2-server-key']) {
                        sh "ssh -o StrictHostKeyChecking=no ubuntu@44.220.134.137 ${dockerCmd}"
                    }
                }
            }
        }
    }
}