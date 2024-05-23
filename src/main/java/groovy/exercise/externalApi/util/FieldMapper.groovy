package groovy.exercise.externalApi.util

import groovy.exercise.externalApi.models.ExternalAPiResponse
import groovy.exercise.externalApi.models.ExternalApiRequest
import groovy.exercise.externalApi.models.StudentDetails
import groovy.exercise.externalApi.models.StudentRequest
import groovy.exercise.externalApi.models.StudentResponse

class FieldMapper {
    static ExternalApiRequest mapToExternalApiRequest(StudentRequest request) {
        new ExternalApiRequest(
                requestId: UUID.randomUUID().toString(),
                serviceCode: request.getSchoolCode(),
                reference: request.getStudentId()
        )
    }

    static StudentResponse mapToStudentResponse(ExternalAPiResponse externalResponse) {
        StudentResponse response = new StudentResponse()
        response.setSuccess(externalResponse.isStatus())
        response.setMessage(externalResponse.getMessage())

        if (externalResponse.isStatus()) {
            StudentDetails details = new StudentDetails()
            details.setStudentId(externalResponse.getData().getBillReference())
            details.setStudentName(externalResponse.getData().getCustomerName())
            details.setGender(externalResponse.getData().getDetails().getGender())
            details.setStudentClass(externalResponse.getData().getCustomerSegment())
            response.setStudentDetails(details)
        }
        response
    }
    }

