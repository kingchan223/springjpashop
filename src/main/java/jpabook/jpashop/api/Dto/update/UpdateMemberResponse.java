package jpabook.jpashop.api.Dto.update;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdateMemberResponse{

    private Long id;
    private String name;
}
