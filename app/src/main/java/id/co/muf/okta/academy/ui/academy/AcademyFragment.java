package id.co.muf.okta.academy.ui.academy;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.List;

import id.co.muf.okta.academy.R;
import id.co.muf.okta.academy.data.CourseEntity;
import id.co.muf.okta.academy.utils.DataDummy;
import id.co.muf.okta.academy.viewmodel.ViewModelFactory;


/**
 * A simple {@link Fragment} subclass.
 */
public class AcademyFragment extends Fragment {
    private RecyclerView rvCourse;
    private ProgressBar progressBar;
    private AcademyAdapter academyAdapter;

    private AcademyViewModel viewModel;
    private List<CourseEntity> courses;

    public AcademyFragment() {
        // Required empty public constructor
    }

    public static Fragment newInstance() {
        return new AcademyFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_academy, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvCourse = view.findViewById(R.id.rv_academy);
        progressBar = view.findViewById(R.id.progress_bar);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getActivity() != null) {
            progressBar.setVisibility(View.VISIBLE);
            viewModel = obtainViewModel(getActivity());
//            courses = viewModel.getCourses();
            academyAdapter = new AcademyAdapter(getActivity());
            viewModel.getCourses().observe(this, courses -> {
                progressBar.setVisibility(View.GONE);
                academyAdapter.setListCourses(courses);
                academyAdapter.notifyDataSetChanged();
            });
            academyAdapter.setListCourses(courses);
            rvCourse.setLayoutManager(new LinearLayoutManager(getContext()));
            rvCourse.setHasFixedSize(true);
            rvCourse.setAdapter(academyAdapter);
        }
    }

    @NonNull
    private static AcademyViewModel obtainViewModel(FragmentActivity activity) {
        // Use a Factory to inject dependencies into the ViewModel
        ViewModelFactory factory = ViewModelFactory.getInstance(activity.getApplication());
        return ViewModelProviders.of(activity, factory).get(AcademyViewModel.class);
    }

}
