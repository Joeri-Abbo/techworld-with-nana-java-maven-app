#!/usr/bin/env  groovy

library identifier: 'jenkins-shared-library@main' retriever: modernSCM(
[$class: 'GitSCMSource', remote: 'https://github.com/Joeri-Abbo/techworld-with-nana-jenkins-shared-libary.git']
credentialsId: "github-credentials"
)
def gv

pipeline {
    agent any
    stages {
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
                    echo "building jar"
                    buildJar()
                }
            }
        }
        stage("build image") {
            steps {
                script {
                    echo "building image"
                    buildImage 'joeri-abbo/demo-app:jma-3.0'
                    dockerLogin()
                    dockerPush 'joeri-abbo/demo-app:jma-3.0'
                }
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
