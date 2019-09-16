package id.co.muf.okta.academy.ui.academy;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import id.co.muf.okta.academy.data.source.local.entity.CourseEntity;
import id.co.muf.okta.academy.data.source.AcademyRepository;
import id.co.muf.okta.academy.utils.FakeDataDummy;
import id.co.muf.okta.academy.vo.Resource;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AcademyViewModelTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private AcademyViewModel viewModel;
    private AcademyRepository academyRepository = mock(AcademyRepository.class);
    private String USERNAME = "Dicoding";

    @Before
    public void setUp() {
        viewModel = new AcademyViewModel(academyRepository);
    }

    @Test
    public void getCourses() {
        //versi1
//        List<CourseEntity> courseEntities = viewModel.getCourses();
//        assertNotNull(courseEntities);
//        assertEquals(5, courseEntities.size());

        //versi2
//        when(academyRepository.getAllCourses()).thenReturn(FakeDataDummy.generateDummyCourses());
//        List<CourseEntity> courseEntities = viewModel.getCourses();
//        verify(academyRepository).getAllCourses();
//        assertNotNull(courseEntities);
//        assertEquals(5, courseEntities.size());

        //versi3
//        ArrayList<CourseEntity> dummyCourses = FakeDataDummy.generateDummyCourses();
//
//        MutableLiveData<List<CourseEntity>> courses = new MutableLiveData<>();
//        courses.setValue(dummyCourses);
//
//        when(academyRepository.getAllCourses()).thenReturn(courses);
//
//        Observer<List<CourseEntity>> observer = mock(Observer.class);
//
//        viewModel.getCourses().observeForever(observer);
//
//        verify(observer).onChanged(dummyCourses);

        //versi4
        Resource<List<CourseEntity>> resource = Resource.success(FakeDataDummy.generateDummyCourses());
        MutableLiveData<Resource<List<CourseEntity>>> dummyCourses = new MutableLiveData<>();
        dummyCourses.setValue(resource);

        when(academyRepository.getAllCourses()).thenReturn(dummyCourses);

        Observer<Resource<List<CourseEntity>>> observer = mock(Observer.class);

        viewModel.setUsername(USERNAME);

        viewModel.courses.observeForever(observer);

        verify(observer).onChanged(resource);

    }

}