document.addEventListener('DOMContentLoaded', () => {
  const sidebarToggle = document.querySelector('[data-sidebar-toggle]');
  const sidebar = document.querySelector('[data-sidebar]');
  const profileToggle = document.querySelector('[data-profile-toggle]');
  const profileMenu = document.querySelector('[data-profile-menu]');
  const toast = document.querySelector('[data-toast]');

  const showToast = (message = 'Saved successfully') => {
    if (!toast) return;
    toast.textContent = message;
    toast.setAttribute('data-open', 'true');
    setTimeout(() => toast.setAttribute('data-open', 'false'), 1800);
  };

  if (sidebarToggle && sidebar) {
    sidebarToggle.addEventListener('click', () => sidebar.classList.toggle('-translate-x-full'));
  }

  if (profileToggle && profileMenu) {
    profileToggle.addEventListener('click', (e) => {
      e.stopPropagation();
      profileMenu.classList.toggle('hidden');
    });
    document.addEventListener('click', (e) => {
      if (!profileMenu.classList.contains('hidden') && !profileMenu.contains(e.target) && !profileToggle.contains(e.target)) {
        profileMenu.classList.add('hidden');
      }
    });
  }

  const closeModal = (modal) => {
    modal.classList.add('hidden');
    modal.classList.remove('flex');
  };

  document.querySelectorAll('[data-modal-open]').forEach((button) => {
    button.addEventListener('click', () => {
      const id = button.getAttribute('data-modal-open');
      const modal = document.getElementById(id || '');
      if (modal) {
        modal.classList.remove('hidden');
        modal.classList.add('flex');
      }
    });
  });

  document.querySelectorAll('[data-modal-close]').forEach((button) => {
    button.addEventListener('click', () => {
      const modal = button.closest('[data-modal]');
      if (modal) closeModal(modal);
    });
  });

  document.querySelectorAll('[data-modal]').forEach((modal) => {
    modal.addEventListener('click', (e) => {
      if (e.target === modal) closeModal(modal);
    });
  });

  document.addEventListener('keydown', (e) => {
    if (e.key === 'Escape') {
      document.querySelectorAll('[data-modal]').forEach((modal) => {
        if (!modal.classList.contains('hidden')) closeModal(modal);
      });
    }
  });

  document.querySelectorAll('[data-tab-group]').forEach((group) => {
    const name = group.getAttribute('data-tab-group');
    const btns = document.querySelectorAll(`[data-tab-button][data-tab-group-name="${name}"]`);
    const panels = document.querySelectorAll(`[data-tab-content-group="${name}"]`);

    btns.forEach((button) => {
      button.addEventListener('click', () => {
        const target = button.getAttribute('data-tab-button');
        btns.forEach((b) => b.setAttribute('data-active', 'false'));
        panels.forEach((panel) => panel.classList.add('hidden'));
        button.setAttribute('data-active', 'true');
        const panel = document.querySelector(`[data-tab-content-group="${name}"][data-tab-content="${target}"]`);
        if (panel) panel.classList.remove('hidden');
      });
    });
  });

  document.querySelectorAll('[data-validate-form]').forEach((form) => {
    form.addEventListener('submit', (e) => {
      let isValid = true;
      form.querySelectorAll('[data-required="true"]').forEach((field) => {
        const input = field;
        const value = input.value?.trim() || '';
        const isEmail = input.getAttribute('type') === 'email';
        const validEmail = /.+@.+\..+/.test(value);
        const valid = value.length > 0 && (!isEmail || validEmail);

        input.classList.toggle('input-invalid', !valid);
        const errorEl = form.querySelector(`[data-error-for="${input.id}"]`);
        if (errorEl) errorEl.classList.toggle('hidden', valid);
        if (!valid) isValid = false;
      });

      if (!isValid) {
        e.preventDefault();
        return;
      }
      e.preventDefault();
      showToast('Form validated successfully');
    });
  });

  const hookFilterTable = ({ inputId, selectId, rowSelector, emptyId, attrName }) => {
    const search = document.getElementById(inputId);
    const filter = document.getElementById(selectId);
    const rows = Array.from(document.querySelectorAll(rowSelector));
    const empty = document.getElementById(emptyId);
    if (!search && !filter) return;

    const apply = () => {
      const q = (search?.value || '').toLowerCase();
      const f = (filter?.value || '').toLowerCase();
      let visible = 0;

      rows.forEach((row) => {
        const text = (row.getAttribute('data-search') || '').toLowerCase();
        const bucket = (row.getAttribute(attrName) || '').toLowerCase();
        const byText = !q || text.includes(q);
        const byFilter = !f || f === 'all' || bucket === f;
        const show = byText && byFilter;
        row.classList.toggle('hidden', !show);
        if (show) visible += 1;
      });

      if (empty) empty.classList.toggle('hidden', visible !== 0);
    };

    search?.addEventListener('input', apply);
    filter?.addEventListener('change', apply);
    apply();
  };

  hookFilterTable({ inputId: 'patientSearch', selectId: 'patientFilter', rowSelector: '[data-patient-row]', emptyId: 'patientEmptyState', attrName: 'data-ward' });
  hookFilterTable({ inputId: 'userSearch', selectId: 'userRoleFilter', rowSelector: '[data-user-row]', emptyId: 'userEmptyState', attrName: 'data-role' });

  const calendarRoot = document.querySelector('[data-calendar-root]');
  if (calendarRoot) {
    const monthNames = ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'];
    const weekdayNames = ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun'];
    const toISO = (date) => {
      const year = date.getFullYear();
      const month = `${date.getMonth() + 1}`.padStart(2, '0');
      const day = `${date.getDate()}`.padStart(2, '0');
      return `${year}-${month}-${day}`;
    };
    const fromISO = (value) => {
      if (!value) return null;
      const [year, month, day] = value.split('-').map(Number);
      if (!year || !month || !day) return null;
      return new Date(year, month - 1, day);
    };
    const startOfWeek = (date) => {
      const d = new Date(date.getFullYear(), date.getMonth(), date.getDate());
      const offset = (d.getDay() + 6) % 7;
      d.setDate(d.getDate() - offset);
      return d;
    };
    const endOfWeek = (date) => {
      const end = new Date(startOfWeek(date));
      end.setDate(end.getDate() + 6);
      return end;
    };

    const grid = calendarRoot.querySelector('[data-calendar-grid]');
    const weekdaysRow = calendarRoot.querySelector('[data-weekdays-row]');
    const label = calendarRoot.querySelector('[data-calendar-label]');
    const viewButtons = calendarRoot.querySelectorAll('[data-calendar-view-btn]');
    const navButtons = calendarRoot.querySelectorAll('[data-calendar-nav]');
    const dateField = document.getElementById('date');
    const bindView = document.querySelector('[data-calendar-bind="view"]');
    const bindPeriodStart = document.querySelector('[data-calendar-bind="periodStart"]');
    const bindPeriodEnd = document.querySelector('[data-calendar-bind="periodEnd"]');
    const initialDate = fromISO(dateField?.value) || new Date();

    const state = {
      view: calendarRoot.getAttribute('data-calendar-initial-view') === 'week' ? 'week' : 'month',
      selectedDate: initialDate,
      anchorDate: initialDate,
    };

    const syncBindings = () => {
      if (dateField) dateField.value = toISO(state.selectedDate);
      if (bindView) bindView.value = state.view;

      const start = state.view === 'month'
        ? new Date(state.anchorDate.getFullYear(), state.anchorDate.getMonth(), 1)
        : startOfWeek(state.anchorDate);
      const end = state.view === 'month'
        ? new Date(state.anchorDate.getFullYear(), state.anchorDate.getMonth() + 1, 0)
        : endOfWeek(state.anchorDate);

      if (bindPeriodStart) bindPeriodStart.value = toISO(start);
      if (bindPeriodEnd) bindPeriodEnd.value = toISO(end);
    };

    const renderWeekdays = () => {
      if (!weekdaysRow) return;
      weekdaysRow.innerHTML = weekdayNames
        .map((day) => `<div class="rounded-lg border border-slate-200 p-2 text-center">${day}</div>`)
        .join('');
    };

    const render = () => {
      if (!grid || !label) return;

      viewButtons.forEach((btn) => btn.setAttribute('data-active', btn.getAttribute('data-calendar-view-btn') === state.view ? 'true' : 'false'));
      renderWeekdays();

      let daysToRender = [];
      if (state.view === 'month') {
        const first = new Date(state.anchorDate.getFullYear(), state.anchorDate.getMonth(), 1);
        const start = startOfWeek(first);
        daysToRender = Array.from({ length: 42 }, (_, i) => {
          const day = new Date(start);
          day.setDate(start.getDate() + i);
          return day;
        });
        label.textContent = `${monthNames[state.anchorDate.getMonth()]} ${state.anchorDate.getFullYear()}`;
      } else {
        const start = startOfWeek(state.anchorDate);
        daysToRender = Array.from({ length: 7 }, (_, i) => {
          const day = new Date(start);
          day.setDate(start.getDate() + i);
          return day;
        });
        label.textContent = `Week of ${daysToRender[0].getDate()} ${monthNames[daysToRender[0].getMonth()]} ${daysToRender[0].getFullYear()}`;
      }

      grid.innerHTML = daysToRender.map((day) => {
        const isoDate = toISO(day);
        const isSelected = isoDate === toISO(state.selectedDate);
        const isOutsideMonth = state.view === 'month' && day.getMonth() !== state.anchorDate.getMonth();
        return `<button type="button" class="day-btn rounded-lg border border-slate-200 p-2 hover:bg-blue-50 ${isOutsideMonth ? 'text-slate-400' : ''}" data-calendar-day data-date="${isoDate}" data-selected="${isSelected ? 'true' : 'false'}">${day.getDate()}</button>`;
      }).join('');

      syncBindings();
    };

    navButtons.forEach((button) => {
      button.addEventListener('click', () => {
        const dir = button.getAttribute('data-calendar-nav') === 'next' ? 1 : -1;
        if (state.view === 'month') {
          state.anchorDate = new Date(state.anchorDate.getFullYear(), state.anchorDate.getMonth() + dir, 1);
        } else {
          state.anchorDate = new Date(state.anchorDate.getFullYear(), state.anchorDate.getMonth(), state.anchorDate.getDate() + (dir * 7));
        }
        render();
      });
    });

    viewButtons.forEach((button) => {
      button.addEventListener('click', () => {
        state.view = button.getAttribute('data-calendar-view-btn') === 'week' ? 'week' : 'month';
        state.anchorDate = new Date(state.selectedDate);
        render();
      });
    });

    grid?.addEventListener('click', (event) => {
      const dayButton = event.target.closest('[data-calendar-day]');
      if (!dayButton) return;
      const day = fromISO(dayButton.getAttribute('data-date'));
      if (!day) return;
      state.selectedDate = day;
      state.anchorDate = day;
      render();
    });

    dateField?.addEventListener('change', () => {
      const day = fromISO(dateField.value);
      if (!day) return;
      state.selectedDate = day;
      state.anchorDate = day;
      render();
    });

    render();
  }

  const slotButtons = document.querySelectorAll('[data-time-slot]');
  slotButtons.forEach((slot) => {
    slot.addEventListener('click', () => {
      slotButtons.forEach((btn) => btn.setAttribute('data-selected', 'false'));
      slot.setAttribute('data-selected', 'true');
      const timeField = document.getElementById('time');
      if (timeField && slot.dataset.slot) timeField.value = slot.dataset.slot;
    });
  });

  document.querySelectorAll('[data-pagination]').forEach((wrap) => {
    const rows = Array.from(wrap.querySelectorAll('[data-page-item]'));
    const prev = wrap.querySelector('[data-page-prev]');
    const next = wrap.querySelector('[data-page-next]');
    const totalEl = wrap.querySelector('[data-page-total]');
    const perPage = Number(wrap.getAttribute('data-per-page') || '5');
    let page = 1;

    const render = () => {
      const total = Math.max(1, Math.ceil(rows.length / perPage));
      page = Math.min(Math.max(1, page), total);
      rows.forEach((row, i) => {
        const start = (page - 1) * perPage;
        row.classList.toggle('hidden', i < start || i >= start + perPage);
      });
      if (totalEl) totalEl.textContent = `Page ${page} of ${total}`;
      if (prev) prev.disabled = page === 1;
      if (next) next.disabled = page === total;
    };

    prev?.addEventListener('click', () => { page -= 1; render(); });
    next?.addEventListener('click', () => { page += 1; render(); });
    render();
  });

  document.querySelectorAll('form[data-toast-message]').forEach((form) => {
    form.addEventListener('submit', (e) => {
      if (form.getAttribute('data-validate-form') === 'true') return;
      e.preventDefault();
      showToast(form.getAttribute('data-toast-message'));
    });
  });
});
