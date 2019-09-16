package id.co.muf.okta.academy.ui.bookmark;

import id.co.muf.okta.academy.data.source.local.entity.CourseEntity;

interface BookmarkFragmentCallback {
    void onShareClick(CourseEntity course);
}
