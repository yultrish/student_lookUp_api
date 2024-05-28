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

        if (externalResponse != null) {
            response.success = externalResponse.status
            response.message = externalResponse.message
            StudentDetails details = new StudentDetails()
            details.studentId = externalResponse.data.billReference
            details.studentName = externalResponse.data.customerName
            details.gender = externalResponse.data.details?.Gender
            details.classs = externalResponse.data.customerSegment
            response.studentDetails = details
        }
        return response
    }
}
