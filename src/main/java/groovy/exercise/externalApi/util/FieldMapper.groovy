package groovy.exercise.externalApi.util

import groovy.exercise.externalApi.models.ExternalAPiResponse
import groovy.exercise.externalApi.models.ExternalApiRequest
import groovy.exercise.externalApi.models.StudentDetails
import groovy.exercise.externalApi.models.StudentRequest
import groovy.exercise.externalApi.models.StudentResponse

class FieldMapper {

    static ExternalApiRequest mapToExternalApiRequest(StudentRequest request) {
        return new ExternalApiRequest(
                requestId: UUID.randomUUID().toString(),
                serviceCode: request.schoolCode,
                reference: request.studentId
        )
    }

    static StudentResponse mapToStudentResponse(ExternalAPiResponse externalResponse) {
        StudentResponse response = new StudentResponse()
        response.success = externalResponse.status
        response.message = externalResponse.message

        if (externalResponse.status) {
            StudentDetails details = new StudentDetails()
            details.studentId = externalResponse.data.billReference
            details.studentName = externalResponse.data.customerName
            details.gender = externalResponse.data.details?.Gender
            details.studentClass = externalResponse.data.customerSegment
            response.studentDetails = details
        }else{
            response.success = false
            response.message = "Failed to retrieve details."
        }

        return response
    }
}
