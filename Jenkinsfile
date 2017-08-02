pipeline {
  agent {
    label 'Slave01'
  }

  stages {
    stage('Unit test') {
      steps {
        sh 'ant -f test.xml -v'
        junit 'reports/result.xml'
      }      
    }

    stage('Build') {
      steps {
        sh 'ant -f build.xml -v'
      }      
    }

    stage('Deploy') {
      steps {
        sh 'sudo aws s3 cp dist/rectangle_${BUILD_NUMBER}.jar s3://eb-artifact-repo/java/jar/rectangle_${BUILD_NUMBER}.jar'
      }      
    }
  }

  post {    
    always {
      archiveArtifacts artifacts: 'dist/*.jar', fingerprint: true   
    }

  }
}
