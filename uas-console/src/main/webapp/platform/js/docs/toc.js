!function($j) {
$j.fn.toc = function(options) {
  var self = this;
  var opts = $j.extend({}, $j.fn.toc.defaults, options);

  var container = $j(opts.container);
  var headings = $j(opts.selectors, container);
  var headingOffsets = [];
  var activeClassName = opts.prefix+'-active';

  var findScrollableElement = function(els) {
    for (var i = 0, argLength = arguments.length; i < argLength; i++) {
      var el = arguments[i],
          $jscrollElement = $j(el);
      if ($jscrollElement.scrollTop() > 0) {
        return $jscrollElement;
      } else {
        $jscrollElement.scrollTop(1);
        var isScrollable = $jscrollElement.scrollTop() > 0;
        $jscrollElement.scrollTop(0);
        if (isScrollable) {
          return $jscrollElement;
        }
      }
    }
    return [];
  };
  var scrollable = findScrollableElement(opts.container, 'body', 'html');

  var scrollTo = function(e) {
    if (opts.smoothScrolling) {
      e.preventDefault();
      var elScrollTo = $j(e.target).attr('href');
      var $jel = $j(elScrollTo);
      
      scrollable.animate({ scrollTop: $jel.offset().top }, 400, 'swing', function() {
        location.hash = elScrollTo;
      });
    }
    $j('li', self).removeClass(activeClassName);
    $j(e.target).parent().addClass(activeClassName);
  };

  //highlight on scroll
  var timeout;
  var highlightOnScroll = function(e) {
    if (timeout) {
      clearTimeout(timeout);
    }
    timeout = setTimeout(function() {
      var top = $j(window).scrollTop();
      for (var i = 0, c = headingOffsets.length; i < c; i++) {
        if (headingOffsets[i] >= top) {
          $j('li', self).removeClass(activeClassName);
          $j('li:eq('+(i-1)+')', self).addClass(activeClassName);
          break;
        }
      }
    }, 50);
  };
  if (opts.highlightOnScroll) {
    $j(window).bind('scroll', highlightOnScroll);
    highlightOnScroll();
  }

  return this.each(function() {
    //build TOC
    var ul = $j('<ul/>');
    headings.each(function(i, heading) {
      var $jh = $j(heading);
      headingOffsets.push($jh.offset().top - opts.highlightOffset);

      //add anchor
      var anchorName = opts.anchorName(i, heading, opts.prefix);
      var anchor = $j([]);
      if (!document.getElementById(anchorName)) {
        anchor = $j('<span/>').attr('id', opts.anchorName(i, heading, opts.prefix)).insertBefore($jh);
      }
      //build TOC item
      var a = $j('<a/>')
      .text($jh.text())
      .attr('href', '#' + anchorName)
      .bind('click', scrollTo);

      var li = $j('<li/>')
      .addClass(opts.prefix+'-'+$jh[0].tagName.toLowerCase())
      .append(a);

      ul.append(li);
    });
    var el = $j(this);
    el.html(ul);
  });
};


$j.fn.toc.defaults = {
  container: 'body',
  selectors: 'h1,h2,h3',
  smoothScrolling: true,
  prefix: '',
  highlightOnScroll: true,
  highlightOffset: 100,
  anchorName: function(i, heading, prefix) {
    return prefix+i;
  } 
};

}(jQuery);