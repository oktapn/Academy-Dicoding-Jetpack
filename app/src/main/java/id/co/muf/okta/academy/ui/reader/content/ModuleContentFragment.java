package id.co.muf.okta.academy.ui.reader.content;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import id.co.muf.okta.academy.R;
import id.co.muf.okta.academy.data.source.local.entity.ContentEntity;
import id.co.muf.okta.academy.data.source.local.entity.ModuleEntity;
import id.co.muf.okta.academy.ui.reader.CourseReaderViewModel;
import id.co.muf.okta.academy.viewmodel.ViewModelFactory;


/**
 * A simple {@link Fragment} subclass.
 */
public class ModuleContentFragment extends Fragment {


    public static final String TAG = ModuleContentFragment.class.getSimpleName();

    private WebView webView;
    private ProgressBar progressBar;
    private CourseReaderViewModel viewModel;

    private Button btnNext;
    private Button btnPrev;

    public static ModuleContentFragment newInstance() {
        return new ModuleContentFragment();
    }

    public ModuleContentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_module_content, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        webView = view.findViewById(R.id.web_view);
        progressBar = view.findViewById(R.id.progress_bar);
        btnNext = view.findViewById(R.id.btn_next);
        btnPrev = view.findViewById(R.id.btn_prev);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getActivity() != null) {
            viewModel = obtainViewModel(getActivity());
            progressBar.setVisibility(View.VISIBLE);
            viewModel.selectedModule.observe(this, moduleEntity -> {
                if (moduleEntity != null) {
                    switch (moduleEntity.status) {
                        case LOADING:
                            progressBar.setVisibility(View.VISIBLE);
                            break;
                        case SUCCESS:
                            if (moduleEntity.data != null) {
                                setButtonNextPrevState(moduleEntity.data);
                                progressBar.setVisibility(View.GONE);
                                if (!moduleEntity.data.isRead()) {
                                    viewModel.readContent(moduleEntity.data);
                                }

                                if (moduleEntity.data.contentEntity != null) {
                                    populateWebView(moduleEntity.data.contentEntity);
                                }
                            }
                            break;
                        case ERROR:
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(getContext(), "Terjadi kesalahan", Toast.LENGTH_SHORT).show();
                            break;
                    }
                }
            });

            btnNext.setOnClickListener(v -> viewModel.setNextPage());

            btnPrev.setOnClickListener(v -> viewModel.setPrevPage());
        }
    }

    private void populateWebView(ContentEntity content) {
        webView.loadData(content.getContent(), "text/html", "UTF-8");
    }

    private void setButtonNextPrevState(ModuleEntity module) {
        if (getActivity() != null) {
            if (module.getPosition() == 0) {
                btnPrev.setEnabled(false);
                btnNext.setEnabled(true);
            } else if (module.getPosition() == viewModel.getModuleSize() - 1) {
                btnPrev.setEnabled(true);
                btnNext.setEnabled(false);
            } else {
                btnPrev.setEnabled(true);
                btnNext.setEnabled(true);
            }
        }
    }

    @NonNull
    private static CourseReaderViewModel obtainViewModel(FragmentActivity activity) {
        // Use a Factory to inject dependencies into the ViewModel
        ViewModelFactory factory = ViewModelFactory.getInstance(activity.getApplication());

        return ViewModelProviders.of(activity, factory).get(CourseReaderViewModel.class);
    }

}
