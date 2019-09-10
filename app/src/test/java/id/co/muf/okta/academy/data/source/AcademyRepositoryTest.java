package id.co.muf.okta.academy.data.source;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import id.co.muf.okta.academy.data.CourseEntity;
import id.co.muf.okta.academy.data.ModuleEntity;
import id.co.muf.okta.academy.data.source.remote.RemoteRepository;
import id.co.muf.okta.academy.data.source.remote.response.ContentResponse;
import id.co.muf.okta.academy.data.source.remote.response.CourseResponse;
import id.co.muf.okta.academy.data.source.remote.response.ModuleResponse;
import id.co.muf.okta.academy.utils.FakeDataDummy;
import id.co.muf.okta.academy.utils.LiveDataTestUtil;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AcademyRepositoryTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private RemoteRepository remote = Mockito.mock(RemoteRepository.class);
    private FakeAcademyRepository academyRepository = new FakeAcademyRepository(remote);

    private ArrayList<CourseResponse> courseResponses = FakeDataDummy.generateRemoteDummyCourses();
    private String courseId = courseResponses.get(0).getId();
    private ArrayList<ModuleResponse> moduleResponses = FakeDataDummy.generateRemoteDummyModules(courseId);
    private String moduleId = moduleResponses.get(0).getModuleId();
    private ContentResponse content = FakeDataDummy.generateRemoteDummyContent(moduleId);

    @Before
    public void setUp() {

    }

    @After
    public void tearDown() {

    }

    @Test
    public void getAllCourses() {
        //versi1
//        when(remote.getAllCourses()).thenReturn(courseResponses);
//        ArrayList<CourseEntity> courseEntities = academyRepository.getAllCourses();
//        verify(remote).getAllCourses();
//        assertNotNull(courseEntities);
//        assertEquals(courseResponses.size(), courseEntities.size());

        //versi2
        doAnswer(invocation -> {
            ((RemoteRepository.LoadCoursesCallback) invocation.getArguments()[0])
                    .onAllCoursesReceived(courseResponses);
            return null;
        }).when(remote).getAllCourses(any(RemoteRepository.LoadCoursesCallback.class));

        List<CourseEntity> result = LiveDataTestUtil.getValue(academyRepository.getAllCourses());

        verify(remote, times(1)).getAllCourses(any(RemoteRepository.LoadCoursesCallback.class));

        assertNotNull(result);
        assertEquals(courseResponses.size(), result.size());
    }

    @Test
    public void getAllModulesByCourse() {
        //versi1
//        when(remote.getModules(courseId)).thenReturn(moduleResponses);
//        ArrayList<ModuleEntity> moduleEntities = academyRepository.getAllModulesByCourse(courseId);
//        verify(remote).getModules(courseId);
//        assertNotNull(moduleEntities);
//        assertEquals(moduleResponses.size(), moduleEntities.size());

        //versi2
        doAnswer(invocation -> {
            ((RemoteRepository.LoadModulesCallback) invocation.getArguments()[1])
                    .onAllModulesReceived(moduleResponses);
            return null;
        }).when(remote).getModules(eq(courseId), any(RemoteRepository.LoadModulesCallback.class));

        List<ModuleEntity> result = LiveDataTestUtil.getValue(academyRepository.getAllModulesByCourse(courseId));

        verify(remote, times(1)).getModules(eq(courseId), any(RemoteRepository.LoadModulesCallback.class));

        assertNotNull(result);
        assertEquals(moduleResponses.size(), result.size());
    }

    @Test
    public void getBookmarkedCourses() {
//        when(remote.getAllCourses()).thenReturn(courseResponses);
//        ArrayList<CourseEntity> courseEntities = academyRepository.getBookmarkedCourses();
//        verify(remote).getAllCourses();
//        assertNotNull(courseEntities);
//        assertEquals(courseResponses.size(), courseEntities.size());
        doAnswer(invocation -> {
            ((RemoteRepository.LoadCoursesCallback) invocation.getArguments()[0])
                    .onAllCoursesReceived(courseResponses);
            return null;
        }).when(remote).getAllCourses(any(RemoteRepository.LoadCoursesCallback.class));

        List<CourseEntity> result = LiveDataTestUtil.getValue(academyRepository.getBookmarkedCourses());

        verify(remote, times(1)).getAllCourses(any(RemoteRepository.LoadCoursesCallback.class));

        assertNotNull(result);
        assertEquals(courseResponses.size(), result.size());
    }

    @Test
    public void getContent() {
        //versi1
//        when(remote.getModules(courseId)).thenReturn(moduleResponses);
//        when(remote.getContent(moduleId)).thenReturn(content);
//        ModuleEntity resultModule = academyRepository.getContent(courseId, moduleId);
//        verify(remote).getContent(moduleId);
//        assertNotNull(resultModule);
//        assertEquals(content.getContent(), resultModule.contentEntity.getContent());

        //versi2
        doAnswer(invocation -> {
            ((RemoteRepository.LoadModulesCallback) invocation.getArguments()[1])
                    .onAllModulesReceived(moduleResponses);
            return null;
        }).when(remote).getModules(eq(courseId), any(RemoteRepository.LoadModulesCallback.class));

        doAnswer(invocation -> {
            ((RemoteRepository.GetContentCallback) invocation.getArguments()[1])
                    .onContentReceived(content);
            return null;
        }).when(remote).getContent(eq(moduleId), any(RemoteRepository.GetContentCallback.class));

        ModuleEntity resultContent = LiveDataTestUtil.getValue(academyRepository.getContent(courseId, moduleId));

        verify(remote, times(1))
                .getModules(eq(courseId), any(RemoteRepository.LoadModulesCallback.class));

        verify(remote, times(1))
                .getContent(eq(moduleId), any(RemoteRepository.GetContentCallback.class));

        assertNotNull(resultContent);
        assertNotNull(resultContent.contentEntity);
        assertNotNull(resultContent.contentEntity.getContent());
        assertEquals(content.getContent(), resultContent.contentEntity.getContent());
    }


    @Test
    public void getCourseWithModules() {
        //versi1
//        when(remote.getAllCourses()).thenReturn(courseResponses);
//        CourseEntity resultCourse = academyRepository.getCourseWithModules(courseId);
//        verify(remote).getAllCourses();
//        assertNotNull(resultCourse);
//        assertEquals(courseResponses.get(0).getTitle(), resultCourse.getTitle());

        //versi2
        doAnswer(invocation -> {
            ((RemoteRepository.LoadCoursesCallback) invocation.getArguments()[0])
                    .onAllCoursesReceived(courseResponses);
            return null;
        }).when(remote).getAllCourses(any(RemoteRepository.LoadCoursesCallback.class));

        CourseEntity result = LiveDataTestUtil.getValue(academyRepository.getCourseWithModules(courseId));

        verify(remote, times(1)).getAllCourses(any(RemoteRepository.LoadCoursesCallback.class));

        assertNotNull(result);
        assertNotNull(result.getTitle());
        assertEquals(courseResponses.get(0).getTitle(), result.getTitle());
    }

}