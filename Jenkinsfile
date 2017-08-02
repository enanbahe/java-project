pipeline {
  agent {
    label: 'Slave01'
  }

  stages {
    stage('build') {
      steps {
        sh 'ant -f build.xml -v'
      }      
    }
  }
}
