package id.co.muf.okta.academy.ui.bookmark;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.PagedList;

import java.util.List;

import id.co.muf.okta.academy.data.source.local.entity.CourseEntity;
import id.co.muf.okta.academy.data.source.AcademyRepository;
import id.co.muf.okta.academy.vo.Resource;

public class BookmarkViewModel extends ViewModel {

    private AcademyRepository academyRepository;

    public BookmarkViewModel(AcademyRepository mAcademyRepository) {
        this.academyRepository = mAcademyRepository;
    }

    LiveData<Resource<List<CourseEntity>>> getBookmarks() {
        return academyRepository.getBookmarkedCourses();
    }

    LiveData<Resource<PagedList<CourseEntity>>> getBookmarksPaged(){
        return academyRepository.getBookmarkedCoursesPaged();
    }

    void setBookmark(CourseEntity courseEntity) {
        final boolean newState = !courseEntity.isBookmarked();
        academyRepository.setCourseBookmark(courseEntity, newState);
    }
}