//package com.profect.delivery.infra.api;
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.http.*;
//import org.springframework.stereotype.Service;
//import org.springframework.web.client.RestTemplate;
//import java.util.List;
//import java.util.Map;
//
//@Service
//@RequiredArgsConstructor
//@Slf4j
//
//public class GeminiApiService {
//
//    @Value("${ai.gemini.url}")
//    private String geminiUrl;
//
//    @Value("${ai.gemini.key}")
//    private String geminiApiKey;
//
//    private final RestTemplate restTemplate = new RestTemplate();
//
//    public String summarizeReviewComments(List<String> comments) {
//        // 1. 리뷰 묶어서 하나의 텍스트로
//        String reviewText = String.join("\n", comments);
//        String prompt = "다음 리뷰들을 요약해서 키워드 형식으로 알려줘:\n" + reviewText;
//
//        // 2. 요청 구성
//        Map<String, Object> textPart = Map.of("text", prompt);
//        Map<String, Object> parts = Map.of("parts", List.of(textPart));
//        Map<String, Object> requestBody = Map.of("contents", List.of(parts));
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//
//        String urlWithKey = geminiUrl + "?key=" + geminiApiKey;
//
//        HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);
//
//        try {
//            ResponseEntity<Map> response = restTemplate.exchange(
//                    urlWithKey,
//                    HttpMethod.POST,
//                    request,
//                    Map.class
//            );
//
//            // 3. 응답 파싱
//            List<Map<String, Object>> candidates = (List<Map<String, Object>>) response.getBody().get("candidates");
//            if (candidates != null && !candidates.isEmpty()) {
//                Map<String, Object> content = (Map<String, Object>) candidates.get(0).get("content");
//                List<Map<String, String>> partsList = (List<Map<String, String>>) content.get("parts");
//
//                return partsList.get(0).get("text"); // 요약 텍스트 반환
//            }
//
//        } catch (Exception e) {
//            log.error("Gemini API 호출 오류", e);
//        }
//
//        return null;
//    }
//}



package com.profect.delivery.infra.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@Service
public class GeminiApiService {

    private final WebClient webClient;

    // application.yml에서 주입
    @Value("${ai.gemini.key}")
    private String geminiApiKey;

    private static final String GEMINI_API_URL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash-latest:generateContent";

    public GeminiApiService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl(GEMINI_API_URL).build();
    }

    /**
     * Gemini API를 호출하여 리뷰 키워드 요약을 생성합니다.
     */
    public String summarizeReviewComments(List<String> comments) {
        if (comments == null || comments.isEmpty()) {
            return "요약할 리뷰가 없습니다.";
        }

        // 프롬프트 구성
        String prompt = buildPrompt(comments);

        try {
            GeminiRequestBody requestBody = new GeminiRequestBody(prompt);

            GeminiResponse response = webClient.post()
                    .uri(uriBuilder -> uriBuilder.queryParam("key", geminiApiKey).build())
                    .bodyValue(requestBody)
                    .retrieve()
                    .bodyToMono(GeminiResponse.class)
                    .block(); // 동기 처리 (비동기로 전환 가능)


            if (response != null && response.candidates() != null && !response.candidates().isEmpty()) {
                return response.candidates().get(0).content().parts().get(0).text();


//            if (response != null && response.getCandidates() != null && !response.getCandidates().isEmpty()) {
//                return response.getCandidates().get(0).getContent().getParts().get(0).getText();
            } else {
                return "요약 결과가 없습니다.";
            }

        } catch (Exception e) {
            log.error("Gemini API 호출 실패: {}", e.getMessage(), e);
            return "요약 실패";
        }
    }
// 인풋을 자세하고 길게 써서 아웃풋을 제한하
    // 프롬프트을 파일 따로 생성해서 가져오는 것도 좋음
    // json
    private String buildPrompt(List<String> comments) {
        String joinedComments = String.join("\n", comments);
        return """
                다음은 고객들이 남긴 음식점 리뷰입니다.
                해당 리뷰들을 분석해서 핵심 키워드 형태로 요약해 주세요.
                예시: '맛있어요', '불친절해요', '청결해요', '양이 적어요'
                5~7개 이내 키워드로 요약해주세요. 문장 형태로 쓰지 말고, 쉼표로 구분해서 나열해주세요.

                리뷰 목록:
                """ + joinedComments;
    }

    // 내부 요청 클래스
    public record GeminiRequestBody(Contents contents) {
        public GeminiRequestBody(String text) {
            this(new Contents(new Part(text)));
        }

        public record Contents(Part[] parts) {
            public Contents(Part part) {
                this(new Part[]{part});
            }
        }

        public record Part(String text) {}
    }

    // 내부 응답 클래스
    public record GeminiResponse(List<Candidate> candidates) {
        public record Candidate(Content content) {}

        public record Content(List<Part> parts) {
            public record Part(String text) {}
        }
    }
}
