package id.co.muf.okta.academy.ui.bookmark;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import id.co.muf.okta.academy.data.CourseEntity;
import id.co.muf.okta.academy.data.source.AcademyRepository;
import id.co.muf.okta.academy.utils.FakeDataDummy;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class BookmarkViewModelTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private BookmarkViewModel viewModel;
    private AcademyRepository academyRepository = mock(AcademyRepository.class);


    @Before
    public void setUp() {
        viewModel = new BookmarkViewModel(academyRepository);
    }

    @Test
    public void getBookmark() {
        //versi1
//        List<CourseEntity> courseEntities = viewModel.getBookmarks();
//        assertNotNull(courseEntities);
//        assertEquals(5, courseEntities.size());

        //versi2
//        when(academyRepository.getBookmarkedCourses()).thenReturn(FakeDataDummy.generateDummyCourses());
//        List<CourseEntity> courseEntities = viewModel.getBookmarks();
//        verify(academyRepository).getBookmarkedCourses();
//        assertNotNull(courseEntities);
//        assertEquals(5, courseEntities.size());

        //versi3
        ArrayList<CourseEntity> dummyCourses = FakeDataDummy.generateDummyCourses();

        MutableLiveData<List<CourseEntity>> courses = new MutableLiveData<>();
        courses.setValue(dummyCourses);

        when(academyRepository.getBookmarkedCourses()).thenReturn(courses);

        Observer<List<CourseEntity>> observer = mock(Observer.class);

        viewModel.getBookmarks().observeForever(observer);

        verify(observer).onChanged(dummyCourses);
    }

}