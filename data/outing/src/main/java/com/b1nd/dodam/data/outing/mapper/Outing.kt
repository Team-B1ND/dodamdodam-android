package com.b1nd.dodam.data.outing.mapper

import com.b1nd.dodam.data.core.toModel
import com.b1nd.dodam.model.OutType
import com.b1nd.dodam.model.Outing
import com.b1nd.dodam.model.Student
import com.b1nd.dodam.model.Teacher
import com.b1nd.dodam.network.outing.model.OutingResponse

internal fun OutingResponse.toModel(type: OutType): Outing {
    return Outing(
        id = id,
        reason = reason,
        type = type,
        status = status.toModel(),
        student = student.toModel(),
        teacher = teacher.toModel(),
        startOutDate = startOutDate,
        endOutDate = endOutDate,
        arrivedDate = arrivedDate,
        checkedDate = checkedDate
    )
}

internal fun StudentResponse.toModel(): Student {
    return Student(
        id = id,
    )
}

internal fun TeacherResponse.toModel(): Teacher {
    return Teacher(
        id = id,
    )
}
