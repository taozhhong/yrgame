/**
 * linkbutton - jQuery EasyUI
 *
 * Licensed under the GPL:
 *   http://www.gnu.org/licenses/gpl.txt
 *
 * Copyright 2010 stworthy [ stworthy@gmail.com ]
 */
(function ($) {
    $.fn.linkbutton = function (options) {

        // wrap the link button, make sure to execute once
        function wrapButton(target) {
            $(target).addClass('l-btn');

            if ($.trim($(target).html().replace(/&nbsp;/g, ' ')) == '') {
                $(target).html('&nbsp;').wrapInner(
                    '<span class="l-btn-left">' +
                        '<span class="l-btn-text">' +
                        '<span class="l-btn-empty"></span>' +
                        '</span>' +
                        '</span>'
                );

                var iconCls = $(target).attr('icon');
                if (iconCls) {
                    $('.l-btn-empty', target).addClass(iconCls);
                }
            } else {
                $(target).wrapInner(
                    '<span class="l-btn-left">' +
                        '<span class="l-btn-text">' +
                        '</span>' +
                        '</span>'
                );

                var cc = $('.l-btn-text', target);
                var iconCls = $(target).attr('icon');
                if (iconCls) {
                    cc.addClass(iconCls).css('padding-left', '20px');
                }
            }
        }

        return this.each(function () {
            var opts;
            var state = $.data(this, 'linkbutton');
            if (state) {
                opts = $.extend(state.options, options || {});
                state.options = opts;
            } else {
                wrapButton(this);
                opts = $.extend({}, $.fn.linkbutton.defaults, options || {});

//				if ($(this).hasClass('l-btn-plain')) {
//					opts.plain = true;
//				}
                if ($(this).attr('plain') == 'true') {
                    opts.plain = true;
                }

                // the button initialize state is disabled
                if ($(this).attr('disabled')) {
                    opts.disabled = true;
                    $(this).removeAttr('disabled');
                }

                state = {options: opts};

            }

            if (state.options.disabled) {
                var href = $(this).attr('href');
                if (href) {
                    state.href = href;
                    $(this).removeAttr('href');
                }
                var onclick = $(this).attr('onclick');
                if (onclick) {
                    state.onclick = onclick;
                    $(this).attr('onclick', null);
                }
                $(this).addClass('l-btn-disabled');
            } else {
                if (state.href) {
                    $(this).attr('href', state.href);
                }
                if (state.onclick) {
                    this.onclick = state.onclick;
                }
                $(this).removeClass('l-btn-disabled');
            }

            if (state.options.plain == true) {
                $(this).addClass('l-btn-plain');
            } else {
                $(this).removeClass('l-btn-plain');
            }

            $.data(this, 'linkbutton', state);	// save the button state
        });
    };

    $.fn.linkbutton.defaults = {
        disabled: false,
        plain: false
    };
})(jQuery);
