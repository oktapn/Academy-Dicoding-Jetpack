package id.co.muf.okta.academy.ui.detail;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import id.co.muf.okta.academy.data.CourseEntity;
import id.co.muf.okta.academy.data.ModuleEntity;
import id.co.muf.okta.academy.data.source.AcademyRepository;
import id.co.muf.okta.academy.utils.FakeDataDummy;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class DetailCourseViewModelTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private DetailCourseViewModel viewModel;
//    private CourseEntity dummyCourse;
    private AcademyRepository academyRepository = mock(AcademyRepository.class);
    private CourseEntity dummyCourse = FakeDataDummy.generateDummyCourses().get(0);
    private String courseId = dummyCourse.getCourseId();
    private ArrayList<ModuleEntity> dummyModules = FakeDataDummy.generateDummyModules(courseId);


    @Before
    public void setUp() {
        viewModel = new DetailCourseViewModel(academyRepository);
        viewModel.setCourseId(courseId);
    }

    @Test
    public void getCourse() {
        //versi1
//        viewModel.setCourseId(dummyCourse.getCourseId());
//        CourseEntity courseEntity = viewModel.getCourse();
//        assertNotNull(courseEntity);
//        assertEquals(dummyCourse.getCourseId(), courseEntity.getCourseId());
//        assertEquals(dummyCourse.getDeadline(), courseEntity.getDeadline());
//        assertEquals(dummyCourse.getDescription(), courseEntity.getDescription());
//        assertEquals(dummyCourse.getImagePath(), courseEntity.getImagePath());
//        assertEquals(dummyCourse.getTitle(), courseEntity.getTitle());

        //versi2
//        when(academyRepository.getCourseWithModules(courseId)).thenReturn(dummyCourse);
//        CourseEntity courseEntity = viewModel.getCourse();
//        verify(academyRepository).getCourseWithModules(courseId);
//        assertNotNull(courseEntity);
//        String courseId =courseEntity.getCourseId();
//        assertNotNull(courseId);
//        assertEquals(dummyCourse.getCourseId(), courseId);

        //versi3
        MutableLiveData<CourseEntity> courseEntities = new MutableLiveData<>();
        courseEntities.setValue(dummyCourse);

        when(academyRepository.getCourseWithModules(courseId)).thenReturn(courseEntities);

        Observer<CourseEntity> observer = mock(Observer.class);

        viewModel.getCourse().observeForever(observer);

        verify(observer).onChanged(dummyCourse);
    }

    @Test
    public void getModules() {
        //versi1
//        List<ModuleEntity> moduleEntities = viewModel.getModules();
//        assertNotNull(moduleEntities);
//        assertEquals(7, moduleEntities.size());

        //versi2
//        when(academyRepository.getAllModulesByCourse(courseId)).thenReturn(FakeDataDummy.generateDummyModules(courseId));
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
}