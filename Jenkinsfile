pipeline{
    agent  any
    tools{
        maven 'maven-3.92'
    }
    stages{
        stage('build  jar'){
            steps{
                script{
                    echo  "building  the application"
                    sh 'mvn clean package'

                }
                
            }
        }
          stage('Build  image'){
            steps{
                script{
                    echo  "building  the docker image"
                    withCredentials ([usernamePassword(credentialsId:'docker-hub-repo',  passwordVariable: 'PASS', usernameVariable: 'USER')]){
                        sh  'docker build -t piratehammad/demo-app:jma-2.0 .'
                        sh "echo  $PASS  | docker login -u $USER --password-stdin"
                        sh 'docker push piratehammad/demo-app:jma-2.0'
                    }

                }
                
            }
        }
        stage ('deploy'){
            steps{
                script{
                    echo "deploying the application"

                }
            }
        }
    }
}
