package id.co.muf.okta.academy.ui.bookmark;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import id.co.muf.okta.academy.data.CourseEntity;
import id.co.muf.okta.academy.data.source.AcademyRepository;
import id.co.muf.okta.academy.utils.DataDummy;

public class BookmarkViewModel extends ViewModel {

    private AcademyRepository academyRepository;

    public BookmarkViewModel(AcademyRepository mAcademyRepository) {
        this.academyRepository = mAcademyRepository;
    }

    LiveData<List<CourseEntity>> getBookmarks() {
        return academyRepository.getBookmarkedCourses();
    }
}