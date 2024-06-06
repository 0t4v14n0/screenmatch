package com.example.screenmatch.service;

import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.service.OpenAiService;
import com.theokanning.openai.OpenAiHttpException;

public class ConsultaChatGPT {

    private static final String API_KEY = "sk-proj-ujHw7ynzwdgwGjQidYoDT3BlbkFJHBJpDR8tP6O8zj5WYeA5";
    private static final int MAX_RETRIES = 3;
    private static final int RETRY_DELAY_MS = 1000; // 1 segundo

    public static String obterTraducao(String texto) {
        OpenAiService service = new OpenAiService(API_KEY);
        CompletionRequest requisicao = CompletionRequest.builder()
                .model("gpt-3.5-turbo-instruct")
                .prompt("traduza para o português o texto: " + texto)
                .maxTokens(1000)
                .temperature(0.7)
                .build();

        for (int attempt = 1; attempt <= MAX_RETRIES; attempt++) {
            try {
                var resposta = service.createCompletion(requisicao);
                return resposta.getChoices().get(0).getText();
            } catch (OpenAiHttpException e) {
                if (e.getMessage().contains("You exceeded your current quota")) {
                    System.err.println("Cota excedida. Verifique seu plano e detalhes de cobrança.");
                    break; // Não adianta tentar novamente se a cota foi excedida
                } else if (e.getMessage().contains("HTTP 429")) {
                    // Retry with delay in case of rate limit exceeded
                    System.err.println("Rate limit exceeded. Tentando novamente em " + RETRY_DELAY_MS + "ms...");
                    try {
                        Thread.sleep(RETRY_DELAY_MS);
                    } catch (InterruptedException ie) {
                        Thread.currentThread().interrupt();
                        System.err.println("Thread interrupted during sleep: " + ie.getMessage());
                    }
                } else {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
                break; // Break on non-HTTP exceptions
            }
        }
        return "Erro ao obter tradução.";
    }
}