package id.co.muf.okta.academy.ui.bookmark;

import id.co.muf.okta.academy.data.CourseEntity;

interface BookmarkFragmentCallback {
    void onShareClick(CourseEntity course);
}
