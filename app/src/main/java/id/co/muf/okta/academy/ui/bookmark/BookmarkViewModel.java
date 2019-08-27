package id.co.muf.okta.academy.ui.bookmark;

import androidx.lifecycle.ViewModel;

import java.util.List;

import id.co.muf.okta.academy.data.CourseEntity;
import id.co.muf.okta.academy.utils.DataDummy;

public class BookmarkViewModel extends ViewModel {

    List<CourseEntity> getBookmarks() {
        return DataDummy.generateDummyCourses();
    }
}