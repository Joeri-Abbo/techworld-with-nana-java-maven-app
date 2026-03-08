def gv
pipeline {
    agent any
    tools {
        maven 'maven-3.92'
    }
    stages {
        stage("init") {
            steps {
                script {
                    gv = load "script.groovy"
                }
            }
        }
        stage("buildjar") {
            steps {
                script{
                    gv.buildJar()
                }
            }
        }
        stage("buildimage") {
            steps {
                script{
                    gv.buildImage()
                }
            }
        }
        stage("deployApp") {
            steps {
                script{
                    gv.deployApp()
                }
            }
        }
    }
}
