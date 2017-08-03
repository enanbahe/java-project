pipeline {
  agent none

  stages {
    stage('Unit test') {
      agent {
        label 'ant'
      }
      steps {
        sh 'ant -f test.xml -v'
        junit 'reports/result.xml'
      }      
    }

    stage('Build') {
      agent {
        label 'ant'
      }
      steps {
        sh 'ant -f build.xml -v'
      }      
	  post {    
	    success {
	      archiveArtifacts artifacts: 'dist/*.jar', fingerprint: true   
	    }
	  }
    }

    stage('Artifact Repo') {
      agent {
        label 'aws'
      }
      steps {
        sh 'sudo aws s3 cp dist/rectangle_${BUILD_NUMBER}.jar s3://eb-artifact-repo/java/jar/rectangle_${BUILD_NUMBER}.jar'
      }      
    }

    stage('Test on Debian') {
      agent {
        docker 'openjdk:8u121-jre'
      }
      steps {
        sh 'mkdir java-test'
        sh 'cd java-test'
        sh 'wget https://s3.amazonaws.com/eb-artifact-repo/java/jar/rectangle_${BUILD_NUMBER}.jar'
        sh 'java -jar rectangle_${BUILD_NUMBER}.jar 3 4'
      }
    }
  }
}
