document.addEventListener('DOMContentLoaded', () => {
    // Lucide Icons Initialization
    if (window.lucide) {
        lucide.createIcons();
    }

    // Sidebar Toggle
    const sidebarToggle = document.querySelector('[data-sidebar-toggle]');
    const sidebar = document.querySelector('[data-sidebar]');
    if (sidebarToggle && sidebar) {
        sidebarToggle.addEventListener('click', (e) => {
            e.stopPropagation();
            sidebar.classList.toggle('-translate-x-full');
        });
        
        // Close sidebar when clicking outside on mobile
        document.addEventListener('click', (e) => {
            if (!sidebar.classList.contains('-translate-x-full') && 
                !sidebar.contains(e.target) && 
                !sidebarToggle.contains(e.target) && 
                window.innerWidth < 1024) {
                sidebar.classList.add('-translate-x-full');
            }
        });
    }

    // Profile Menu Toggle
    const profileToggle = document.querySelector('[data-profile-toggle]');
    const profileMenu = document.querySelector('[data-profile-menu]');
    if (profileToggle && profileMenu) {
        profileToggle.addEventListener('click', (e) => {
            e.stopPropagation();
            profileMenu.classList.toggle('hidden');
            profileMenu.classList.toggle('animate-in');
            profileMenu.classList.toggle('fade-in');
            profileMenu.classList.toggle('zoom-in-95');
            profileMenu.classList.toggle('slide-in-from-top-2');
        });
        document.addEventListener('click', (e) => {
            if (!profileMenu.classList.contains('hidden') && !profileMenu.contains(e.target)) {
                profileMenu.classList.add('hidden');
            }
        });
    }

    // Toast Notifications
    const toast = document.querySelector('[data-toast]');
    const showToast = (message = 'Done!', type = 'success') => {
        if (!toast) return;
        toast.innerHTML = `
            <div class="flex items-center gap-3">
                <i data-lucide="${type === 'success' ? 'check-circle' : 'alert-circle'}" class="w-5 h-5 ${type === 'success' ? 'text-emerald-400' : 'text-red-400'}"></i>
                <span class="font-medium">${message}</span>
            </div>
        `;
        lucide.createIcons();
        toast.setAttribute('data-open', 'true');
        setTimeout(() => toast.setAttribute('data-open', 'false'), 3000);
    };

    // Tab Switching
    document.querySelectorAll('[data-tab-group]').forEach((group) => {
        const name = group.getAttribute('data-tab-group');
        const buttons = document.querySelectorAll(`[data-tab-button][data-tab-group-name="${name}"]`);
        const panels = document.querySelectorAll(`[data-tab-content-group="${name}"]`);

        buttons.forEach((button) => {
            button.addEventListener('click', () => {
                const target = button.getAttribute('data-tab-button');
                buttons.forEach((btn) => btn.setAttribute('data-active', 'false'));
                panels.forEach((panel) => panel.classList.add('hidden'));
                
                button.setAttribute('data-active', 'true');
                const targetPanel = document.querySelector(`[data-tab-content-group="${name}"][data-tab-content="${target}"]`);
                if (targetPanel) targetPanel.classList.remove('hidden');
            });
        });
    });

    // Simple Tooltip logic
    document.querySelectorAll('[data-tooltip]').forEach(el => {
        el.addEventListener('mouseenter', () => {
            // Future tooltip implementation
        });
    });

    // Modal Toggle
    document.querySelectorAll('[data-modal-open]').forEach((btn) => {
        btn.addEventListener('click', () => {
            const modalId = btn.getAttribute('data-modal-open');
            const modal = document.getElementById(modalId);
            if (modal) {
                modal.classList.remove('hidden');
                modal.classList.add('flex');
            }
        });
    });

    document.querySelectorAll('[data-modal-close]').forEach((btn) => {
        btn.addEventListener('click', () => {
            const modal = btn.closest('[data-modal]');
            if (modal) {
                modal.classList.add('hidden');
                modal.classList.remove('flex');
            }
        });
    });

    // Handle Form Toasts
    document.querySelectorAll('form[data-toast-message]').forEach((form) => {
        form.addEventListener('submit', (e) => {
            e.preventDefault();
            const message = form.getAttribute('data-toast-message');
            showToast(message, 'success');
            
            const modal = form.closest('[data-modal]');
            if (modal) {
                modal.classList.add('hidden');
                modal.classList.remove('flex');
            }
            form.reset();
        });
    });

    // Patient Search & Filter logic
    const patientSearch = document.getElementById('patientSearch');
    const patientFilter = document.getElementById('patientFilter');
    const patientRows = document.querySelectorAll('[data-patient-row]');
    const emptyState = document.getElementById('patientEmptyState');

    if (patientSearch || patientFilter) {
        const filterPatients = () => {
            const query = patientSearch?.value.toLowerCase() || '';
            const ward = patientFilter?.value || 'all';
            let visibleCount = 0;

            patientRows.forEach(row => {
                const text = row.getAttribute('data-search').toLowerCase();
                const rowWard = row.getAttribute('data-ward');
                
                const matchesSearch = text.includes(query);
                const matchesWard = ward === 'all' || rowWard === ward;

                if (matchesSearch && matchesWard) {
                    row.classList.remove('hidden');
                    visibleCount++;
                } else {
                    row.classList.add('hidden');
                }
            });

            if (emptyState) {
                if (visibleCount === 0) emptyState.classList.remove('hidden');
                else emptyState.classList.add('hidden');
            }
        };

        patientSearch?.addEventListener('input', filterPatients);
        patientFilter?.addEventListener('change', filterPatients);
    }
});
