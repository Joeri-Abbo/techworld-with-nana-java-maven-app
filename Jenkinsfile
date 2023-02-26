pipeline {
    agent any
   stages {
       stage("copy files to ansible server")
       steps {
           scripts {
               echo "copying files to ansible server"
               sshagent(['ansible-server-key']) {
                   sh "scp -o StrictHostKeyChecking=no  ansible/* root@127.0.0.1:/root"
                }
                withCredentials([sshUserPrivateKey(credentialsId: 'ec2-server-key', keyFileVariable: 'keyfile', usernameVariable: 'user')]){
                    sh "scp $keyfile root@127.0.0.1:/root/ssh-key.pem"
                }
           }
       }
   }
}
