package com.b1nd.dodam.club

import com.b1nd.dodam.club.api.ClubService
import com.b1nd.dodam.club.model.ClubJoinResponse
import com.b1nd.dodam.club.model.ClubMemberResponse
import com.b1nd.dodam.club.model.ClubResponse
import com.b1nd.dodam.network.core.DodamUrl
import com.b1nd.dodam.network.core.model.TeacherResponse
import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.MockRequestHandleScope
import io.ktor.client.engine.mock.respond
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.HttpResponseData
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.fullPath
import io.ktor.http.headersOf
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class ClubServiceTest {
    private lateinit var clubService: ClubService
    private val testDispatcher = StandardTestDispatcher()

    @BeforeTest
    fun setup() {
        val httpClient = HttpClient(MockEngine) {
            install(ContentNegotiation) {
                json()
            }
            engine {
                addHandler { request ->
                    val path = "https://" + request.url.host + request.url.fullPath
                    val method = request.method.value

                    fun MockRequestHandleScope.makeOkRespond(content: String): HttpResponseData =
                        respond(
                            content = content,
                            status = HttpStatusCode.OK,
                            headers = headersOf(HttpHeaders.ContentType, "application/json"),
                        )
                    when {
                        DodamUrl.Club.JOIN_REQUEST + "/1" == path && method == "POST" -> {
                            makeOkRespond(
                                content = """
                                    {
                                      "message": "성공적인 해결",
                                      "status": 200
                                    }
                                """.trimIndent()
                            )
                        }
                        DodamUrl.Club.JOIN_REQUEST == path && method == "GET" -> {
                            makeOkRespond(
                                content = """
                                    {
                                      "message": "동아리 입부 신청 성공",
                                      "status": 200
                                    }
                                """.trimIndent()
                            )
                        }

                        DodamUrl.Club.JOIN_REQUEST + "/1" == path && method == "DELETE" -> {
                            makeOkRespond(
                                content = """
                                    {
                                      "message": "성공적인 거절",
                                      "status": 200
                                    }
                                """.trimIndent()
                            )
                        }

                        DodamUrl.CLUB + "/1" == path && method == "GET" -> {
                            makeOkRespond(
                                content = """
                                    {
                                        "status": 200,
                                        "message": "동아리 상세 정보",
                                        "data": {
                                            "id": "1",
                                            "name": "B1ND",
                                            "shortDescription": "도담도담을 유지 보수 합니다.",
                                            "description": "코딩을 좋아하는 바인드",
                                            "subject": "개발",
                                            "type": "CREATIVE_ACTIVITY_CLUB",
                                            "teacher": {
                                                "name": "제프 딘",
                                                "position": "구글",
                                                "tel": "010-1234-5678"
                                            },
                                            "state": "ALLOWED"
                                        }
                                    }
                                """.trimIndent()
                            )
                        }

                        DodamUrl.Club.JOIN_REQUEST + "/received" == path && method == "GET" -> {
                            makeOkRespond(
                                content = """
                                    {
                                        "status": 200,
                                        "message": "받은 부원 제안",
                                        "data": [
                                            {
                                                "id": "1",
                                                "clubPermission": "CLUB_MEMBER",
                                                "status": "ALLOWED",
                                                "club": {
                                                    "id": "1",
                                                    "name": "B1ND",
                                                    "shortDescription": "도담도담을 유지 보수 합니다.",
                                                    "description": "코딩을 좋아하는 바인드",
                                                    "subject": "subject가 뭐지",
                                                    "type": "CREATIVE_ACTIVITY_CLUB",
                                                    "teacher": {
                                                        "name": "제프 딘",
                                                        "position": "구글",
                                                        "tel": "010-1234-5678"
                                                    },
                                                    "state": "ALLOWED"
                                                }
                                            }
                                        ]
                                    }
                                """.trimIndent()
                            )
                        }

                        DodamUrl.CLUB + "/1/leader" == path && method == "GET" -> {
                            makeOkRespond(
                                content = """
                                    {
                                        "status": 200,
                                        "message": "부장 로드됨",
                                        "data": {
                                            "id": 46,
                                            "status": "ALLOWED",
                                            "permission": "CLUB_LEADER",
                                            "studentId": 1,
                                            "name": "김민규",
                                            "grade": 2,
                                            "room": 2,
                                            "number": 8,
                                            "profileImage": "https://avatars.githubusercontent.com/u/93782306?s=280&v=4"
                                         }
                                    }
                                """.trimIndent()
                            )
                        }

                        DodamUrl.CLUB + "/1/members" == path && method == "GET" -> {
                            makeOkRespond(
                                content = """
                                    {
                                        "status": 200,
                                        "message": "string",
                                        "data": [
                                            {
                                                "id": 39,
                                                "status": "ALLOWED",
                                                "permission": "CLUB_LEADER",
                                                "studentId": 2,
                                                "name": "김하나",
                                                "grade": 2,
                                                "room": 2,
                                                "number": 8,
                                                "profileImage": "https://avatars.githubusercontent.com/u/93782306?s=280&v=4"
                                            }
                                        ]
                                    }
                                """.trimIndent()
                            )
                        }

                        DodamUrl.CLUB + "/1/all-members" == path && method == "GET" -> {
                            makeOkRespond(
                                content = """
                                    {
                                        "status": 200,
                                        "message": "string",
                                        "data": [
                                            {
                                                "id": 48,
                                                "status": "WAITING",
                                                "permission": "CLUB_MEMBER",
                                                "studentId": 5,
                                                "name": "박재민",
                                                "grade": 2,
                                                "room": 2,
                                                "number": 8,
                                                "profileImage": null
                                            }
                                        ]
                                    }
                                """.trimIndent()
                            )
                        }

                        DodamUrl.CLUB == path && method == "GET" -> {
                            makeOkRespond(
                                content = """
                                    {
                                        "status": 200,
                                        "message": "전체 동아리",
                                        "data": [
                                            {
                                                "id": 1,
                                                "name": "B1ND",
                                                "shortDescription": "바인드는 짱입니다.",
                                                "description": "어서 빨리 지원하세요",
                                                "subject": "what is subject?",
                                                "image": "https://avatars.githubusercontent.com/u/93782306?s=280&v=4",
                                                "type": "CREATIVE_ACTIVITY_CLUB",
                                                "teacher": {
                                                    "name": "제프 딘",
                                                    "position": "구글",
                                                    "tel": "010-1234-5678"
                                                },
                                                "state": "ALLOWED"
                                            }
                                        ]
                                    }
                                """.trimIndent()
                            )
                        }
                        DodamUrl.CLUB + "/joined" == path && method == "GET" -> {
                            makeOkRespond(
                                content = """
                                    {
                                        "status": 200,
                                        "message": "나의 동아리 불러오기 성공",
                                        "data": [
                                            {
                                                "id": 5,
                                                "name": "B1ND",
                                                "shortDescription": "설명",
                                                "description": "설명22",
                                                "subject": "전공",
                                                "image": "이미지",
                                                "type": "CREATIVE_ACTIVITY_CLUB",
                                                "teacher": null,
                                                "state": "ALLOWED"
                                            }
                                        ]
                                    }
                                """.trimIndent()
                            )
                        }

                        DodamUrl.CLUB + "/my" == path && method == "GET" -> {
                            makeOkRespond(
                                content = """
                                        {
                                            "status": 200,
                                            "message": "사용자의 동아리 상태 불러오기 성공",
                                            "data": [
                                                {
                                                    "createdAt": "2025-03-01T02:27:07.942185",
                                                    "modifiedAt": "2025-03-01T02:27:07.942185",
                                                    "id": 8,
                                                    "name": "B4ND",
                                                    "shortDescription": "설명55555",
                                                    "description": "설명666666",
                                                    "image": "이미지",
                                                    "subject": "전공",
                                                    "type": "CREATIVE_ACTIVITY_CLUB",
                                                    "teacher": null,
                                                    "state": "PENDING"
                                                }
                                            ]
                                        }
                                """.trimIndent()
                            )
                        }

                        else -> error("Unhandled $path")
                    }


                }
            }
        }
        clubService = ClubService(httpClient)
    }

    @Test
    fun 동아리_부원_제안_수락() = runTest(testDispatcher) {
        val response = clubService.postClubJoinRequestsAllow(1)
        assertEquals(
            Unit,
            response,
        )
    }

    @Test
    fun 동아리_입부_신청() = runTest(testDispatcher) {
        val response = clubService.postClubJoinRequests(clubId = 5, clubPriority = "CREATIVE_ACTIVITY_CLUB_1", introduce = "열심히 하겠습니다.")
        assertEquals(
            Unit,
            response,
        )
    }

    @Test
    fun 동아리_부원_제안_거절() = runTest(testDispatcher) {
        val response = clubService.deleteClubJoinRequests(1)
        assertEquals(
            Unit,
            response,
        )
    }

    @Test
    fun 동아리_상세_검색() = runTest(testDispatcher) {
        val response = clubService.getDetailClub(1)
        assertEquals(
            ClubResponse(
                id = 1,
                name = "B1ND",
                shortDescription = "도담도담을 유지 보수 합니다.",
                description = "코딩을 좋아하는 바인드",
                subject = "개발",
                type = "CREATIVE_ACTIVITY_CLUB",
                teacher = TeacherResponse(
                    name = "제프 딘",
                    position = "구글",
                    tel = "010-1234-5678"
                ),
                state = "ALLOWED"
            ),
            response
        )
    }

    @Test
    fun 받은_부원_제안_검색() = runTest(testDispatcher) {
        val response = clubService.getClubJoinRequestReceived()
        assertEquals(
            ClubJoinResponse(
                id = 1,
                clubPermission = "CLUB_MEMBER",
                status = "ALLOWED",
                club = ClubResponse(
                    id = 1,
                    name = "B1ND",
                    shortDescription = "도담도담을 유지 보수 합니다.",
                    description = "코딩을 좋아하는 바인드",
                    subject = "subject가 뭐지",
                    type = "CREATIVE_ACTIVITY_CLUB",
                    teacher = TeacherResponse(
                        name = "제프 딘",
                        position = "구글",
                        tel = "010-1234-5678"
                    ),
                    state = "ALLOWED"
                )
            ),
            response.first()
        )
    }

    @Test
    fun 부장_가져오기() = runTest(testDispatcher) {
        val response = clubService.getClubLeader(1)
        assertEquals(
            ClubMemberResponse(
                id = 46,
                status = "ALLOWED",
                permission = "CLUB_LEADER",
                studentId = 1,
                name = "김민규",
                grade = 2,
                room = 2,
                number = 8,
                profileImage = "https://avatars.githubusercontent.com/u/93782306?s=280&v=4"
            ), response
        )
    }

    @Test
    fun 멤버_가져오기() = runTest(testDispatcher) {
        val response = clubService.getClubAllowMember(1)

        assertEquals(
            ClubMemberResponse(
                id = 39,
                status = "ALLOWED",
                permission = "CLUB_LEADER",
                studentId = 2,
                name = "김하나",
                grade = 2,
                room = 2,
                number = 8,
                profileImage = "https://avatars.githubusercontent.com/u/93782306?s=280&v=4"
            ),
            response.first()
        )
    }



    @Test
    fun 모든_멤버_가져오기() = runTest(testDispatcher) {
        val response = clubService.getClubAllMember(1)

        assertEquals(
            ClubMemberResponse(
                id = 48,
                status = "WAITING",
                permission = "CLUB_MEMBER",
                studentId = 5,
                name = "박재민",
                grade = 2,
                room = 2,
                number = 8,
                profileImage = null
            ),
            response.first()
        )
    }

    @Test
    fun 동아리_가져오기() = runTest(testDispatcher) {
        val response = clubService.getClubList()
        assertEquals(
            ClubResponse(
                id = 1,
                name = "B1ND",
                shortDescription = "바인드는 짱입니다.",
                description = "어서 빨리 지원하세요",
                subject = "what is subject?",
                image = "https://avatars.githubusercontent.com/u/93782306?s=280&v=4",
                type = "CREATIVE_ACTIVITY_CLUB",
                teacher = TeacherResponse(
                    name = "제프 딘",
                    position = "구글",
                    tel = "010-1234-5678"
                ),
                state = "ALLOWED"
            ),
            response.first()
        )
    }

    @Test
    fun 입부한_동아리_가져오기() = runTest(testDispatcher) {
        val response = clubService.getClubJoined()
        assertEquals(
            ClubResponse(
                id = 5,
                name = "B1ND",
                shortDescription = "설명",
                description = "설명22",
                subject = "전공",
                image = "이미지",
                type = "CREATIVE_ACTIVITY_CLUB",
                teacher = null,
                state = "ALLOWED"
            ),
            response.first()
        )
    }

    @Test
    fun 사용자가_개설_신청한_동아리_상태_가져오기() = runTest(testDispatcher) {
        val response = clubService.getClubMyCreated()
        assertEquals(
            ClubResponse(
                createdAt = "2025-03-01T02:27:07.942185",
                modifiedAt = "2025-03-01T02:27:07.942185",
                id = 8,
                name = "B4ND",
                shortDescription = "설명55555",
                description = "설명666666",
                subject = "전공",
                image = "이미지",
                type = "CREATIVE_ACTIVITY_CLUB",
                teacher = null,
                state = "PENDING"
            ),
            response.first()
        )
    }
}