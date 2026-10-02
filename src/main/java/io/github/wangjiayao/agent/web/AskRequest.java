package io.github.wangjiayao.agent.web;
import jakarta.validation.constraints.NotBlank;
public record AskRequest(@NotBlank String sessionId, @NotBlank String question) { }
