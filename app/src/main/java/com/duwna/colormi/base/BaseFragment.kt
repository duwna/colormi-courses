package com.duwna.colormi.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.duwna.colormi.MainActivity

abstract class BaseFragment<T : BaseViewModel<out IViewModelState>> : Fragment() {

    val root: MainActivity
        get() = activity as MainActivity

    protected abstract val viewModel: T
    protected abstract val layout: Int

    abstract fun setupViews()
    abstract fun subscribeOnState(state: IViewModelState)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(layout, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()
        viewModel.observeState(viewLifecycleOwner) { state -> subscribeOnState(state) }
        viewModel.observeNotifications(viewLifecycleOwner) { root.renderNotification(it) }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                activity?.onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}