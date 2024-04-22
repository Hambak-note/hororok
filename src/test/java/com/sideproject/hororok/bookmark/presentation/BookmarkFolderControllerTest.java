package com.sideproject.hororok.bookmark.presentation;

import com.sideproject.hororok.auth.dto.LoginMember;
import com.sideproject.hororok.common.annotation.ControllerTest;
import com.sideproject.hororok.bookmark.dto.request.BookmarkFolderSaveRequest;
import com.sideproject.hororok.bookmark.dto.request.BookmarkFolderUpdateRequest;
import com.sideproject.hororok.bookmark.dto.response.BookmarkFoldersResponse;
import com.sideproject.hororok.bookmark.dto.response.BookmarksResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.http.MediaType;

import static com.sideproject.hororok.common.fixtures.BookmarkFixtures.북마크_리스트_응답;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static com.sideproject.hororok.common.fixtures.BookmarkFolderFixtures.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


class BookmarkFolderControllerTest extends ControllerTest {

    private static final String AUTHORIZATION_HEADER_NAME = "Authorization";
    private static final String AUTHORIZATION_HEADER_VALUE = "Bearer fake-token";


    @Test
    @DisplayName("하단 탭의 \"저장\" 버튼을 눌렀을 때 필요한 정보 제공한다.")
    public void test_bookmark_folders() throws Exception {

        BookmarkFoldersResponse fakeResponse = 북마크_폴더_응답();

        when(bookmarkFolderService
                .bookmarkFolders(any(LoginMember.class)))
                .thenReturn(fakeResponse);

        mockMvc.perform(
                        get("/api/bookmark/folders")
                                .header(AUTHORIZATION_HEADER_NAME, AUTHORIZATION_HEADER_VALUE)
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.folderCount").value(폴더_개수1))
                .andExpect(jsonPath("$.folders").isArray())
                .andExpect(jsonPath("$.folders", hasSize(폴더_리스트_사이즈)))
                .andExpect(jsonPath("$.folders[" + 폴더_리스트_인덱스1 + "].folderId").value(폴더_ID_1))
                .andExpect(jsonPath("$.folders[" + 폴더_리스트_인덱스1 + "].name").value(즐겨찾기_폴더_이름1));

    }

    @Test
    @DisplayName("북마크 폴더를 저장하는 기능을 테스트한다.")
    public void test_folder_save() throws Exception {

        //given
        BookmarkFolderSaveRequest request = 폴더_저장_요청();
        BookmarkFoldersResponse expectedResponse = 북마크_폴더_응답();

        //when
        when(bookmarkFolderService.save(any(BookmarkFolderSaveRequest.class), any(LoginMember.class)))
                .thenReturn(expectedResponse);

        //then
        mockMvc.perform(
                        post("/api/bookmark/folder/save")
                                .header(AUTHORIZATION_HEADER_NAME, AUTHORIZATION_HEADER_VALUE)
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("북마크 폴더를 수정하는 기능을 테스트한다")
    public void test_folder_update() throws Exception {

        BookmarkFolderUpdateRequest request = 폴더_수정_요청();
        BookmarkFoldersResponse expectedResponse = 북마크_폴더_응답();

        when(bookmarkFolderService.update(any(BookmarkFolderUpdateRequest.class), any(LoginMember.class)))
                .thenReturn(expectedResponse);

        //then
        mockMvc.perform(
                        put("/api/bookmark/folder/update")
                                .header(AUTHORIZATION_HEADER_NAME, AUTHORIZATION_HEADER_VALUE)
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("북마크 폴더를 삭제하는 기능을 테스트 한다.")
    public void test_folder_delete() throws Exception {



        BookmarkFoldersResponse expectedResponse = 북마크_폴더_응답();
        when(bookmarkFolderService.delete(any(Long.class), any(LoginMember.class)))
                .thenReturn(expectedResponse);

        //then
        mockMvc.perform(
                        delete("/api/bookmark/folder/{folderId}/delete", 폴더_ID_1)
                                .header(AUTHORIZATION_HEADER_NAME, AUTHORIZATION_HEADER_VALUE)
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }


    @Test
    @DisplayName("북마크 폴더 지도 노출 여부를 변경하는 기능을 테스트 한다")
    public void test_update_folder_visible() throws Exception {

        Long folderId = 폴더_ID_1;

        //then
        mockMvc.perform(
                        put("/api/bookmark/folder/{folderId}/update/visible", folderId)
                                .header(AUTHORIZATION_HEADER_NAME, AUTHORIZATION_HEADER_VALUE)
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

    }

    @Test
    @DisplayName("북마크 폴더를 조회하면 북마크의 리스트를 리턴해야 한다.")
    public void test_bookmarks() throws Exception {


        Long folderId = 폴더_ID_1;

        BookmarksResponse expectedResponse = 북마크_리스트_응답();
        when(bookmarkService.bookmarks(any(Long.class))).thenReturn(expectedResponse);

        mockMvc.perform(
                        get("/api/bookmark/folder/{folderId}", folderId)
                                .header(AUTHORIZATION_HEADER_NAME, AUTHORIZATION_HEADER_VALUE)
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}