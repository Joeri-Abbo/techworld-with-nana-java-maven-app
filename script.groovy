def buildjar() {
    echo 'building the application...'
        sh 'mvn package'
}

def buildimage() {
    echo  "building  the docker image"
                    withCredentials([usernamePassword(credentialsId:'docker-hub-repo',  passwordVariable: 'PASS', usernameVariable: 'USER')]){
                        sh  'docker build -t piratehammad/demo-app:jma-2.0 .'
                        sh "echo  $PASS  | docker login -u $USER --password-stdin"
                        sh 'docker push piratehammad/demo-app:jma-2.0'
                    }
}
def deployApp() {
    echo 'deploying the application...'
}

return this
