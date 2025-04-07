package com.jpatest.jpatest.dto;

import com.jpatest.jpatest.entity.Member;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import javax.print.attribute.standard.Destination;

@Getter
@Setter
public class MemberDto {
    private String memberId;
    private String password;
    private String name;
    private String email;
    private String tel;

    private static ModelMapper modelMapper = new ModelMapper();

    static{
        modelMapper.typeMap(MemberDto.class, Member.class)
                .addMappings(mapper -> {
                    mapper.skip(Member::setId); // Dto와 entity와 변수가 다른경우 매핑 제외
                });
    }

    // entity - >  dto
    public static MemberDto of(Member member){
        return modelMapper.map(member , MemberDto.class);
    }

    //dto - > entity
    public Member createMember(){
        return modelMapper.map(this, Member.class);
    }

}
