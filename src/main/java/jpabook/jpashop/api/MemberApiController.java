package jpabook.jpashop.api;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import jpabook.jpashop.api.Dto.create.CreateMemberRequest;
import jpabook.jpashop.api.Dto.create.CreateMemberResponse;
import jpabook.jpashop.api.Dto.update.UpdateMemberRequest;
import jpabook.jpashop.api.Dto.update.UpdateMemberResponse;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@JsonAutoDetect
public class MemberApiController {

    private final MemberService memberService;

    @PostMapping("api/v1/members")
    public CreateMemberResponse saveMemberV1(@RequestBody @Valid Member member){
        Long id = memberService.join(member);
        return new CreateMemberResponse(id);
    }

    @PostMapping("api/v2/members")
    public CreateMemberResponse saveMemberV2(@RequestBody @Valid CreateMemberRequest request){
        Member member = new Member();
        member.setName(request.getName());
        Long id = memberService.join(member);
        return new CreateMemberResponse(id);
    }

    @PutMapping("api/v2/members/{id}")
    public UpdateMemberResponse updateMemberV2(@PathVariable Long id,
                                               @RequestBody @Valid UpdateMemberRequest request){
        memberService.update(id, request.getName());//커맨드와 쿼리 분리
        Member one = memberService.findOne(id);//커맨드와 쿼리 분리
        return new UpdateMemberResponse(one.getId(), one.getName());
    }
}
