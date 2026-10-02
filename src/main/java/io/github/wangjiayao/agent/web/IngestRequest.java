package io.github.wangjiayao.agent.web;
import jakarta.validation.constraints.NotBlank;
public record IngestRequest(@NotBlank String title, @NotBlank String content) { }
