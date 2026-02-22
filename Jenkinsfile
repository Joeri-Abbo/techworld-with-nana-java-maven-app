def gv
pipeline {
    agent any
    parameters {
        choice(name: 'VERSION', choices: ['1.1.0', '1.2.0', '1.3.0'], description:'')
        booleanParam(name: 'executeTests', defaultValue: true, description:'')
    }
    
    stages {
        stage ("init"){
            steps {
                script {
                    gv = load "script.groovy"
                }
            }
        }
        stage("build") {
            steps {
                script {
                    gv.buildApp()
                }
            }
        }
        stage("test") {
            when {
                expression {
                    params.executeTests
                }
            }
            steps {
                script {
                    gv.testApp()
                }
            }
        }
        stage("deploy") {
            input {
                message "select the environment to deploy to"
                ok "env selected"
                parameters {
                    choice(name: 'ENV', choices: ['dev', 'qa', 'prod'], description:'')
                }
            }  // ← Added missing closing brace here
            steps {
                script {
                    gv.deployApp()
                    echo "deploying to ${ENV}"  // ← Changed to ${ENV} instead of ${params.ENV}
                }
            }
        }
    }
}
