pipeline {
  agent {
    label: 'Slave01'
  }

  stages {
    stage('build') {
      sh 'ant -f build.xml -v'
    }
  }
}
