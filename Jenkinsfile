pipeline {
    agent any
    stages {
        stage('build') {
            steps {
                echo 'Building...'
            }
        }  // ← Missing closing brace added
        stage('test') {
            steps {
                echo 'Testing...'
            }
        }  // ← Missing closing brace added
        stage('deploy') {
            steps {
                echo 'Deploying...'
            }
        }  // ← This one was already there (last stage)
    }
}
