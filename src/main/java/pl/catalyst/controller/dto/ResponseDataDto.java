package pl.catalyst.controller.dto;


import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseDataDto<T> {

    private T data;

    public static <T> ResponseDataDto<T> toResponse(T t){
        return ResponseDataDto
                .<T>builder()
                .data(t)
                .build();
    }
}
