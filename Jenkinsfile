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
        sh 'sudo aws s3 cp dist/rectangle_${BUILD_NUMBER}.jar s3://eb-artifact-repo/java/branches/${BRANCH_NAME}/rectangle_${BUILD_NUMBER}.jar'
      }      
    }

    stage('Test on Debian') {
      agent {
        docker 'openjdk:8u121-jre'
        label 'docker'
      }
      steps {
        sh 'mkdir java-test'
        sh 'cd java-test'
        sh 'wget https://s3.amazonaws.com/eb-artifact-repo/java/branches/${BRANCH_NAME}/rectangle_${BUILD_NUMBER}.jar'
        sh 'java -jar rectangle_${BUILD_NUMBER}.jar 3 4'
      }
    }

    stage('Promote to master') {
      agent {
        label 'ant'
      }
      when {
        branch 'development'
      }
      steps {
        echo 'Stashing any local changes...'
        sh 'git stash'
        echo 'Checking out development branch...'
        sh 'git checkout development'
        echo 'Checking out master branch...'
        sh 'git checkout master'
        echo 'Merging development into master...'
        sh 'git merge development'
        echo 'Pushing to origin master...'
        sh 'git push origin master'
      }
    }

    stage('Release') {
      agent {
        label 'aws'
      }
      when {
        branch 'master'
      }
      steps {
        sh 'sudo aws s3 cp dist/rectangle_${BUILD_NUMBER}.jar s3://eb-artifact-repo/java/releases/rectangle_${BUILD_NUMBER}.jar'
      }      
    }
  }
}
