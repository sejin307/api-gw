node {
  stage('========== Checkout Repository ==========') {
      checkout scm
    }
  
    stage('========== Build Application ==========') {
        sh 'chmod +x ./gradlew'
        sh './gradlew clean build'
    }
  
    stage('========== Build Image ==========') {
      app = docker.build("api-gw", ".")
    }
  
    stage('========== Push Image ==========') {
      docker.withRegistry('https://269923429649.dkr.ecr.ap-northeast-2.amazonaws.com/api-gw', 'ecr:ap-northeast-2:api-gw') {
        app.push("${env.BUILD_NUMBER}")
        app.push("latest")
      }
    }

 //Jenkins 플러그인 설치 필요.
 //AWS Steps (Jenkinsfile 에서 withAWS 사용), Pipeline Utility Steps (Jenkinsfile readJSON사용)
     stage('========== Update ECS Service ==========') {
        withAWS(credentials: 'api-gw') {
            def executionRoleArn = "arn:aws:iam::269923429649:role/ecsTaskExecutionRole" //IAM 정책 명
            def cpu = "4096" //ECS vCPU 2048 > 4096
            def memory = "8192" //ECS Memory 4096 > 8192
            def containerName = "CEN-APIGW-Task" //태스크 명
            def containerDefName = "api-gw" //태스크의 컨테이너 명
            def image = "269923429649.dkr.ecr.ap-northeast-2.amazonaws.com/api-gw:" + env.BUILD_NUMBER //ECR ID
            def logGroup = "/ecs/CEN-APIGW-Task" //로그그룹
            def region = "ap-northeast-2" //리전

            def taskdef = sh(script: """
                aws ecs register-task-definition --family "${containerName}" --network-mode "awsvpc" --requires-compatibilities "FARGATE" --execution-role-arn "${executionRoleArn}" --region "${region}" --tags key=Service,value=API_GW --cpu "${cpu}" --memory "${memory}" --container-definitions "[{\\"name\\": \\"${containerDefName}\\",\\"image\\": \\"${image}\\",\\"cpu\\": ${cpu},\\"memory\\": ${memory},\\"essential\\": true, \\"portMappings\\": [{\\"containerPort\\": 8080, \\"protocol\\": \\"tcp\\"}], \\"logConfiguration\\": { \\"logDriver\\": \\"awslogs\\", \\"options\\": { \\"awslogs-group\\": \\"${logGroup}\\", \\"awslogs-region\\": \\"${region}\\", \\"awslogs-stream-prefix\\": \\"ecs\\"}}}]"
            """, returnStdout: true)


            // taskdef 결과 JSON 파싱후 결과값 추출
            def taskdefJson = readJSON text: taskdef
            def newRevision = taskdefJson.taskDefinition.revision
            def taskDefinitionArn = taskdefJson.taskDefinition.taskDefinitionArn

            // task definition tag등록
            sh "aws ecs tag-resource --resource-arn \"${taskDefinitionArn}\" --tags key=Service,value=API_GW --region \"${region}\" "

            def clusterName = "Cengroup-APIGW-Cluster" //ECS 클러스터명
            def serviceName = "CEN_APIGW-Service" //ECS 서비스명
            def taskDefinition = "CEN-APIGW-Task:" + newRevision //ECS 태스크정의

            sh "aws ecs update-service --cluster \"${clusterName}\" --service \"${serviceName}\" --task-definition \"${taskDefinition}\" --region \"${region}\""

        }
    }
}
