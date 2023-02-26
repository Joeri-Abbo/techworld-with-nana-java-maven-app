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
       stage("run ansible playbook"){
           steps {
               scripts {
                   echo "running ansible playbook"
                   def remote = [:]
                   remote.name = "ansible-server"
                   remote.host = "127.0.0.1"
                   remote.allowAnyHosts = true
                   withCredentials([sshUserPrivateKey(credentialsId: 'ansible-server-key', keyFileVariable: 'keyfile', usernameVariable: 'user')])
                        remote.user = user
                        remote.identityFile = keyfile
                        sshCommand remote: remote, command: "ansible-playbook my-playbook.yaml"
               }
           }
       }
   }
}
