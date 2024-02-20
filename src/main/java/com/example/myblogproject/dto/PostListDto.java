package com.example.myblogproject.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostListDto {
   private long totalPosts;
   private int pageNo;
   private int pageSize;
   private int totalPages;

   private boolean isFirst;
   private boolean isLast;
   private Collection<PostResponseDto> posts;
}
