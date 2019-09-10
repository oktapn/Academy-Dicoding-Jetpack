package id.co.muf.okta.academy.ui.reader;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import id.co.muf.okta.academy.data.ContentEntity;
import id.co.muf.okta.academy.data.CourseEntity;
import id.co.muf.okta.academy.data.ModuleEntity;
import id.co.muf.okta.academy.data.source.AcademyRepository;
import id.co.muf.okta.academy.utils.FakeDataDummy;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CourseReaderViewModelTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private CourseReaderViewModel viewModel;
//    private ContentEntity dummyContentEntity;
//    private String moduleId;
    private AcademyRepository academyRepository = mock(AcademyRepository.class);
    private CourseEntity dummyCourse = FakeDataDummy.generateDummyCourses().get(0);
    private String courseId = dummyCourse.getCourseId();
    private ArrayList<ModuleEntity> dummyModules = FakeDataDummy.generateDummyModules(courseId);
    private String moduleId = dummyModules.get(0).getModuleId();

    @Before
    public void setUp() {
//        viewModel = new CourseReaderViewModel();
//        viewModel.setCourseId("a14");
//
//        moduleId = "a14m1";
//
//        String title = viewModel.getModules().get(0).getTitle();
//        dummyContentEntity = new ContentEntity("<h3 class=\\\"fr-text-bordered\\\">" + title + "</h3><p>Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.</p>");
        viewModel = new CourseReaderViewModel(academyRepository);
        viewModel.setCourseId(courseId);
    }

    @Test
    public void getModules() {
        //versi1
//        ArrayList<ModuleEntity> moduleEntities = viewModel.getModules();
//        assertNotNull(moduleEntities);
//        assertEquals(7, moduleEntities.size());

        //versi2
//        when(academyRepository.getAllModulesByCourse(courseId)).thenReturn(dummyModules);
//        List<ModuleEntity> moduleEntities = viewModel.getModules();
//        verify(academyRepository).getAllModulesByCourse(courseId);
//        assertNotNull(moduleEntities);
//        assertEquals(7, moduleEntities.size());

        //versi3
        MutableLiveData<List<ModuleEntity>> moduleEntities = new MutableLiveData<>();
        moduleEntities.setValue(dummyModules);

        when(academyRepository.getAllModulesByCourse(courseId)).thenReturn(moduleEntities);

        Observer<List<ModuleEntity>> observer = mock(Observer.class);
        viewModel.getModules().observeForever(observer);

        verify(observer).onChanged(dummyModules);
    }

    @Test
    public void getSelectedModule() {
        //versi1
//        viewModel.setSelectedModule(moduleId);
//        ModuleEntity moduleEntity = viewModel.getSelectedModule();
//        assertNotNull(moduleEntity);
//        ContentEntity contentEntity = moduleEntity.contentEntity;
//        assertNotNull(contentEntity);
//        String content = contentEntity.getContent();
//        assertNotNull(content);
//        assertEquals(content, dummyContentEntity.getContent());

        //versi2
//        ModuleEntity moduleEntity = dummyModules.get(0);
//        String content = "<h3 class=\"fr-text-bordered\">Modul 0 : Introduction</h3><p>Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.</p>";
//        moduleEntity.contentEntity = new ContentEntity(content);
//        viewModel.setSelectedModule(moduleId);
//        when(academyRepository.getContent(courseId, moduleId)).thenReturn(moduleEntity);
//        ModuleEntity entity = viewModel.getSelectedModule();
//        verify(academyRepository).getContent(courseId, moduleId);
//        assertNotNull(entity);
//
//        ContentEntity contentEntity = entity.contentEntity;
//        assertNotNull(contentEntity);
//
//        String resultContent = contentEntity.getContent();
//        assertNotNull(resultContent);
//
//        assertEquals(content, resultContent);

        //versi3
        MutableLiveData<ModuleEntity> moduleEntity = new MutableLiveData<>();

        ModuleEntity dummyModule = dummyModules.get(0);
        String content = "<h3 class=\"fr-text-bordered\">Modul 0 : Introduction</h3><p>Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.</p>";
        dummyModule.contentEntity = new ContentEntity(content);

        moduleEntity.setValue(dummyModule);

        when(academyRepository.getContent(courseId, moduleId)).thenReturn(moduleEntity);

        viewModel.setSelectedModule(moduleId);

        Observer<ModuleEntity> observer = mock(Observer.class);

        viewModel.getSelectedModule().observeForever(observer);

        verify(observer).onChanged(dummyModule);
    }

}