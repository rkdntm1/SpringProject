var dateFormatService = (function() {
	//책 기준 418쪽 스타일 포기. 유튜브 스타일 채용.
	function convertToWhen(timeVal) {
		var now = new Date();
		var gapInMilliSec = now.getTime() - timeVal;
		var gapInSec = gapInMilliSec / 1000;
		if (gapInMilliSec < 60)
			return [Math.ceil(gapInSec), '초 전'].join('');
		var gapInMIn = gapInSec / 60;
		if (gapInMIn < 60)
			return [Math.ceil(gapInMIn), '분 전'].join(''); 	
		var gapInHour = gapInMIn / 60;
		if (gapInHour < 24)
			return [Math.ceil(gapInHour), '시간 전'].join('');
		var gapInDay = gapInHour / 24;
		if (gapInDay < 7)
			return [Math.ceil(gapInDay), '일 전'].join('');
		var gapInWeek = gapInDay / 7;
		if (gapInWeek < 5)
			return [Math.ceil(gapInWeek), '주 전'].join('');	
		var gapInMon = gapInDay / 30.5;
		if (gapInMon < 12)
			return [Math.ceil(gapInMon), '달 전'].join('');
		var gapInYear = gapInDay / 365;
		return [Math.ceil(gapInYear), '년 전'].join('');
	}

	
	//정의된 함수를 알려 주는 곳
	return {
		getWhenPosted:convertToWhen
		};
})();