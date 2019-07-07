package nick.uitestingfun.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.fragment_main.*
import nick.uitestingfun.R
import nick.uitestingfun.util.Resource
import nick.uitestingfun.vm.LineupViewModel

class LineupFragment : Fragment() {

    private val adapter = LineupAdapter()
    private val viewModel: LineupViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycler_view.adapter = adapter
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.fetchItems().observe(viewLifecycleOwner, Observer { resource ->
            error_message.visibility = View.GONE
            progress_bar.visibility = View.GONE

            when (resource) {
                is Resource.Loading -> {
                    progress_bar.visibility = View.VISIBLE
                    resource.data?.let(adapter::submitList)
                }
                is Resource.Success -> {
                    adapter.submitList(resource.data)
                }
                is Resource.Error -> {
                    error_message.visibility = View.VISIBLE
                    adapter.submitList(resource.data)
                    Log.e("asdf", "Failed to fetch lineup items", resource.throwable)
                }
            }
        })
    }
}