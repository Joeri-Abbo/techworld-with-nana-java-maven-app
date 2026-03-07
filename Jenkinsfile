def gv
pipeline{
    agent  any
    tools{
        maven 'maven-3.92'
    }
    stages{
        stage("init"){
            steps{
                script{
                    gv  =  load  "script.groovy"
                }
            }
        }
        stage('buildJar'){
            steps{
                script{
                    gv.buildJar()

                }
                
            }
        }
          stage('Buildimage'){
            steps{
                script{
                    gv.buildimage()

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
