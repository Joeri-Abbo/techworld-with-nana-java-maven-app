def gv
### This is a Jenkins pipeline script that defines a CI/CD process for building a Java application using Maven, creating a Docker image, and deploying the application. The script loads an external Groovy script (script.groovy) that contains the implementation of the buildJar, buildImage, and deployApp methods. Each stage of the pipeline corresponds to a specific step in the CI/CD process, allowing for modular and organized execution of tasks.
# hi
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
        stage("build jar") {
            steps {
                script {
                    gv.buildJar()
                }
            }
        }
        stage("build image") {
            steps {
                script {
                    gv.buildImage()
                }
            }
        }
        stage("deploy") {
            steps {
                script {
                    gv.deployApp()
                }
            }
        }
    }
}
